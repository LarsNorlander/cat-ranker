package me.larsnorlander.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by larsjosephnorlander on 2/4/17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not all strands were included in the list of preferences")
class IncompletePreferencesException extends RuntimeException {
}
