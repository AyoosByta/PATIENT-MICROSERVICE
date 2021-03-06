package com.bytatech.ayoos.client.dms_core.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.bytatech.ayoos.client.dms_core.ClientConfiguration;

@FeignClient(name="${dmsCore.name:dmsCore}", url="${dmsCore.url:https://aozd73.trial.alfresco.com/alfresco/api/-default-/public/alfresco/versions/1}", configuration = ClientConfiguration.class)
public interface SitesApiClient extends SitesApi {
}