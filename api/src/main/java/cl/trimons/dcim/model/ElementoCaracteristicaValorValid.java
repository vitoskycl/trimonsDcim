package cl.trimons.dcim.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import cl.trimons.dcim.service.ElementoCaracteristicaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Check that valor is present and available when a new ElementoCaracteristica is created.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ElementoCaracteristicaValorValid.ElementoCaracteristicaValorValidValidator.class
)
public @interface ElementoCaracteristicaValorValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ElementoCaracteristicaValorValidValidator implements ConstraintValidator<ElementoCaracteristicaValorValid, String> {

        private final ElementoCaracteristicaService elementoCaracteristicaService;
        private final HttpServletRequest request;

        public ElementoCaracteristicaValorValidValidator(
                final ElementoCaracteristicaService elementoCaracteristicaService,
                final HttpServletRequest request) {
            this.elementoCaracteristicaService = elementoCaracteristicaService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("valor");
            if (currentId != null) {
                // only relevant for new objects
                return true;
            }
            String error = null;
            if (value == null) {
                // missing input
                error = "NotNull";
            } else if (elementoCaracteristicaService.valorExists(value)) {
                error = "Exists.elementoCaracteristica.valor";
            }
            if (error != null) {
                cvContext.disableDefaultConstraintViolation();
                cvContext.buildConstraintViolationWithTemplate("{" + error + "}")
                        .addConstraintViolation();
                return false;
            }
            return true;
        }

    }

}
