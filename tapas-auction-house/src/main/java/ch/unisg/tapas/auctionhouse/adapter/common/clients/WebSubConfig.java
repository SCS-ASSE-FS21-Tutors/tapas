package ch.unisg.tapas.auctionhouse.adapter.common.clients;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebSubConfig {
    @Getter
    @Value("${environment}")
    private String environment;

    @Getter
    @Value("${auction.house.uri}")
    private String ownAddress;

    @Getter
    @Value("${auction.house.uri.development}")
    private String ownAddressDevelopment;

    @Getter
    @Value("${websub.hub}")
    private String webSubHubAddress;

    @Getter
    @Value("${websub.hub.development}")
    private String webSubHubAddressDevelopment;

    @Getter
    @Value("${topic}")
    private String topic;

    @Getter
    @Value("${group}")
    private String group;

    @Getter
    @Value("${bootstrap}")
    private boolean boostrap;

    public boolean isProductionEnvironment() {
        return environment.equalsIgnoreCase("production");
    }

    public String getHub() {
        return isProductionEnvironment() ? webSubHubAddress : webSubHubAddressDevelopment;
    }

    public String getSelf() {
        return isProductionEnvironment() ? ownAddress : ownAddressDevelopment;
    }

    public String getSelfTopic() {
        return getSelf() + topic;
    }
}
