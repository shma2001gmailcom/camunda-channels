package org.misha.contractor;

import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Component
public class Adder {

    public Future<Integer> sum(int left, int right) {
        return supplyAsync(() -> left + right);
    }
}
