package com.repo.app.service;

import com.repo.app.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    private static final String LANGUAGE = "language";

    Map<String, HashMap<String, Object>> languagesCache = new HashMap<>();

    final RestTemplate restTemplate = new RestTemplate();

    @Value("${git.path}")
    private String gitPath;

    @Value("${git.result.limit}")
    private int gitResultLimit;

    public Object getTrendingLanguagesBasedOnTime() {

        try {
            String getWorkFlowsUrl = String.format(gitPath, LocalDate.now().minusMonths(1));
            ResponseEntity<HashMap> response = restTemplate.getForEntity(getWorkFlowsUrl, HashMap.class);
            if (response.getStatusCode().is2xxSuccessful() & response.getBody() != null && response.getBody().get("items") instanceof List) {
                Map<String, HashMap<String, Object>> languagesData = collectLanguagesData((List<HashMap<Object, Object>>) response.getBody().get("items"));
                return Utils.sortResultDescending(languagesData, languagesCache);
            } else {
                throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
            }
        } catch (RestClientException restClientException) {
            if (restClientException instanceof HttpClientErrorException.Forbidden) {
                return languagesCache;
            }
            throw new RuntimeException(restClientException.getMessage());
        }
    }


    private Map<String, HashMap<String, Object>> collectLanguagesData(List<HashMap<Object, Object>> items) {
        List<HashMap<Object, Object>> itemsResponse = items.stream().limit(gitResultLimit).collect(Collectors.toList());

        Set<String> languages = itemsResponse.stream().filter(map -> map.get(LANGUAGE) != null).map(hashMap -> hashMap.get(LANGUAGE).toString()).collect(Collectors.toSet());

        return languages
                .stream().collect(Collectors.toMap(langName -> langName,
                        langRepo -> {
                            Set<HashMap<Object, Object>> langRepos = itemsResponse.stream().filter(map -> map.get(LANGUAGE) != null && map.get(LANGUAGE).equals(langRepo)).collect(Collectors.toSet());
                            HashMap<String, Object> details = new HashMap<>();
                            details.put("ReposCount", langRepos.size());
                            details.put("ReposURls", langRepos);
                            return details;
                        }
                ));
    }


}
