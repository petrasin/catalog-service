package com.polarbookshop.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
public class PolarProperties {

    /**
     * A message to welcome users.
     */
    private String greeting;

    private Testdata testdata;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public Testdata getTestdata() {
        return testdata;
    }

    public void setTestdata(Testdata testdata) {
        this.testdata = testdata;
    }

    public static class Testdata {

        /**
         * Should load testdata.
         */
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
