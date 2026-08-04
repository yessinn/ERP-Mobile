# Fix MalformedJsonException in Login

The user is experiencing a `MalformedJsonException` during login, which typically indicates that the server's response is not valid JSON. The error message specifically suggests using `JsonReader.setLenient(true)`.

## Proposed Changes

### [Component] Network Layer

#### [MODIFY] [RetrofitClient.kt](file:///C:/Users/LENOVO/AndroidStudioProjects/ERPMobile/app/src/main/java/com/allsoft/erpmobile/data/remote/RetrofitClient.kt)
- Configure `Gson` with `setLenient(true)` to be more tolerant of malformed JSON.
- Add `HttpLoggingInterceptor` to help debug the actual response body from the server.

#### [MODIFY] [build.gradle.kts](file:///C:/Users/LENOVO/AndroidStudioProjects/ERPMobile/app/build.gradle.kts)
- Add `logging-interceptor` dependency.

## Verification Plan

### Manual Verification
- Re-run the login flow and check the Logcat for the raw response body.
- Verify if the `MalformedJsonException` persists or if it reveals a different error (like a parsing error into the `LoginResponse` DTO).
