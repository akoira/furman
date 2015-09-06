import java.util.concurrent.*;

public class Executor {

	private ExecutorService taskService = Executors.newCachedThreadPool();

	public <R> void execute(final Task<R> task) {
		Callable<R> callableTask = new Callable<R>() {
			@Override
			public R call() throws Exception {
				return task.execute();
			}
		};

		final Future<R> future = taskService.submit(callableTask);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						R result = future.get(100, TimeUnit.MILLISECONDS);
						task.successResult(result);
					} catch (InterruptedException e) {
						task.interruptedResult();
						break;
					} catch (ExecutionException e) {
						task.failedResult(e);
						break;
					} catch (TimeoutException e) {
					}
				}
			}
		};

		taskService.execute(runnable);


	}
}
