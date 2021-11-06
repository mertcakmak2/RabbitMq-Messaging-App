package com.example.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String simpSessionId;
    private String simpSubscriptionId;
    private String simpDestination;

}
