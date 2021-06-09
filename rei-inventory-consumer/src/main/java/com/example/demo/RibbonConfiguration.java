package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.ZonePreferenceServerListFilter;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;

public class RibbonConfiguration {

	@Autowired
    IClientConfig config;
 
    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }
 
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
    
    @Bean
	public ZonePreferenceServerListFilter serverListFilter() {
		ZonePreferenceServerListFilter filter = new ZonePreferenceServerListFilter();
		filter.setZone("USA");
		return filter;
	}
}
