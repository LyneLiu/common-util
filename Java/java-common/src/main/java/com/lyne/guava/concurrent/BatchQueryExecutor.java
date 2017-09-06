package com.lyne.guava.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.ForClosure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 并行批处理类
 */
public class BatchQueryExecutor {

	// 对集合元素执行的次数
	private final static int FUTUREQUERYNUMBER = 1;

	private final static int TIMEOUTMINISECONDS = 3000;

    private static Logger logger = LoggerFactory.getLogger(BatchQueryExecutor.class);

	public <IN, OUT> List<OUT> executeQuery(final Collection<IN> inputs, final BatchQuery<IN, OUT> executeUnit) {
		ListenableFuture<List<OUT>> futures = submitBatchTaskFutures(inputs, executeUnit);
		delegateAsynTask(futures);
		return getAsynResults(futures);
	}

	private <IN, OUT> ListenableFuture<List<OUT>> submitBatchTaskFutures(final Collection<IN> inputs,
			final BatchQuery<IN, OUT> executeUnit) {
		final Set<ListenableFuture<OUT>> result = new HashSet<ListenableFuture<OUT>>(inputs.size());
		final ListeningExecutorService service = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(inputs.size()));
		Closure futureQuery = new Closure() {
			public void execute(Object input) {
				final IN p = (IN) input;
				result.add(service.submit(new Callable<OUT>() {
					@Override
					public OUT call() throws Exception {
						return executeUnit.query(p);
					}
				}));
			}
		};
		Closure parallelTask = new ForClosure(FUTUREQUERYNUMBER, futureQuery);
		CollectionUtils.forAllDo(inputs, parallelTask);
		service.shutdown();
		return Futures.allAsList(result);
	}

	private <OUT> OUT getAsynResults(final ListenableFuture<OUT> futures)  {
		try {
			return futures.get(TIMEOUTMINISECONDS,TimeUnit.MILLISECONDS );
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <TYPE> void delegateAsynTask(final ListenableFuture<TYPE> allFutures) {
		Futures.addCallback(allFutures, new FutureCallback<TYPE>() {
			@Override
			public void onSuccess(final TYPE result) {
				System.out.println("success...");
			}

			@Override
			public void onFailure(final Throwable thrown) {
				System.out.println("failure...");
				logger.error("BatchQueryExecutorException",thrown);
			}
		});
	}
}