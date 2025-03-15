package com.example.quartz_kafka.request;

public class QuartzKafkaRequest {

    private String topicName;

    private Record record;

    public QuartzKafkaRequest() {
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public static class Record {

        private int id;

        private String value;

        public Record() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
