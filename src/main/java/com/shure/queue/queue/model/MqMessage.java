package com.shure.queue.queue.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MqMessage<T> implements Serializable {
    private String topic;
    private T content;
}
