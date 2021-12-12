package carregistry.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result<T> {
    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";
    private Date timestamp = new Date();
    private String status;
    private String path;

    private T data;
    private List<Exception> errors = new ArrayList();

    private Result(final String status) {
        this.status = status;
    }

    public Result() {
        this.status = "ok";
    }

    public Result(final T data) {
        this.status = "ok";
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result("ok");
    }

    public static <T> Result<T> ok(final T data) {
        Result<T> res = new Result("ok");
        res.data = data;
        return res;
    }

    public static <T> Result<T> error() {
        return new Result("error");
    }

    public static <T> Result<T> error(final Exception error) {
        Result<T> result = new Result("error");
        result.errors = new ArrayList();
        result.errors.add(error);
        return result;
    }

    public static <T> Result<T> error(final List<Exception> errors) {
        Result<T> result = new Result("error");
        result.errors = errors;
        return result;
    }

    @JsonIgnore
    public T getDataOrThrow() throws Exception {
        if (Objects.equals("ok", this.getStatus())) {
            return this.getData();
        } else {
            throw new Exception((Throwable) this.getErrors());
        }
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getStatus() {
        return this.status;
    }

    public String getPath() {
        return this.path;
    }


    public T getData() {
        return this.data;
    }

    public List<Exception> getErrors() {
        return this.errors;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public void setData( final T data) {
        this.data = data;
    }

    public void setErrors(final List<Exception> errors) {
        this.errors = errors;
    }

    public String toString() {
        String var10000 = super.toString();
        return "Result(super=" + var10000 + ", timestamp=" + this.getTimestamp() + ", status=" + this.getStatus() + ", path=" + this.getPath() + ", data=" + this.getData() + ", errors=" + this.getErrors() + ")";
    }
}
