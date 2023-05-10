package com.example.peaksoftlmsb8.dto.response;

import java.util.Map;

public class LinkResponse {
        private Map<String, String> link;

        public LinkResponse(Map<String, String> link) {
            this.link = link;
        }

        public Map<String, String> getLink() {
            return link;
        }

        public void setLink(Map<String, String> link) {
            this.link = link;
        }
    }

