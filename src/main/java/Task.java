public interface Task<R>{
	public void successResult(R result);
	public void failedResult(Exception exception);
	public void interruptedResult();
	public void timeoutResult();
	public R execute();
}
