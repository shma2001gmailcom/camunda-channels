package org.misha.contractor;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class Adder {
    public Future<Integer> sum(int left, int right) {
        return CompletableFuture.supplyAsync(() -> left + right);
    }
}
