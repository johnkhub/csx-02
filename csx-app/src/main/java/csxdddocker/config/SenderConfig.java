package csxdddocker.config;



//====import org.apache.activemq.ActiveMQConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Configuration
public class SenderConfig {
    Logger logger = LoggerFactory.getLogger(SenderConfig.class);
    ///@Value("${activemq.broker-url}")
    private String brokerUrl="tcp://localhost:61616";
/*===
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        logger.info("initializing ActiveMQConnectionFactory");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        logger.info("Setting up broker "+brokerUrl);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(senderActiveMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(cachingConnectionFactory());
    }
*/
    /*
    @Bean
    public Sender sender() {
        return new Sender();
    }
*/

}
