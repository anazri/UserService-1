package com.cclogic.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Nishant on 9/20/2017.
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Contact {

    private int id;
    private String name;
    private String phoneNumber;
}
