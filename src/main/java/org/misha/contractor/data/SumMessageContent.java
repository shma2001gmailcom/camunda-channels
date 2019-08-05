package org.misha.contractor.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SumMessageContent {
    private int sum;
    private int left;
    private int right;
}
