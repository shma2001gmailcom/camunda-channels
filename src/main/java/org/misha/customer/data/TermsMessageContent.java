package org.misha.customer.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TermsMessageContent {
    private String id;
    private int left;
    private int right;
}
