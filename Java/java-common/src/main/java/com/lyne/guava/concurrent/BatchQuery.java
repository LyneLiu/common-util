package com.lyne.guava.concurrent;

public interface BatchQuery<IN, OUT> {
	OUT query(IN input) throws Exception;
}
