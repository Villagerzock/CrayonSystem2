package net.crayonsmp.utils.ingredient;

public class NotRequiredIngredientTypeException extends RuntimeException {
    public NotRequiredIngredientTypeException(String requiredType,String givenType) {
        super("Recipe Ingredient has Given Type: " + givenType + " but the Recipe Type requires: " + requiredType);
    }
}
