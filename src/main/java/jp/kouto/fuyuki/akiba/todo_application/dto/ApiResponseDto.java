package jp.kouto.fuyuki.akiba.todo_application.dto;

import jp.kouto.fuyuki.akiba.todo_application.enums.ApiResult;

public class ApiResponseDto<T> {
	private final int status;
	private final ApiResult result;
	private final T data;
	
	private ApiResponseDto(Builder<T> builder) {
		this.status = builder.status;
		this.result = builder.result;
		this.data = builder.data;
	}
	
	public static <T> Builder<T> builder(){
		return new Builder<>();
	}
	
	public int getStatus() {
		return status;
	}
	
	public ApiResult getResult() {
		return result;
	}
	
	public T getData() {
		return data;
	}
	
	public static class Builder<T> {
		private int status;
		private ApiResult result;
		private T data;
		
		public Builder<T> status(int status) {
			this.status = status;
			return this;
		}
		
		public Builder<T> result(ApiResult result){
			this.result = result;
			return this;
		}
		
		public Builder<T> data(T data){
			this.data = data;
			return this;
		}
		
		public ApiResponseDto<T> build(){
			return new ApiResponseDto<>(this);
		}
	}

}
