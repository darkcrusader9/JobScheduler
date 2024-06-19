package com.example.jobscheduler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ExecutorConfig {
    private int numThreads;

    public ExecutorConfig(int numThreads) {
        this.numThreads = numThreads;
    }
}