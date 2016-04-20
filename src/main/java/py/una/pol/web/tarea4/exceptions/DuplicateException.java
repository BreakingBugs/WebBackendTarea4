package py.una.pol.web.tarea4.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = false)
public class DuplicateException extends Exception {
}