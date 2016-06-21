package com.sccomponents.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A custom observer attachable to a generic class.
 *
 * v1.0.0
 */
@SuppressWarnings("unused")
public class ScObserver {

    /****************************************************************************************
     * Privates and protected variable
     */

    private Object mToObserve;
    private HashMap<Method, Object> mStatus;


    /****************************************************************************************
     * Constructor
     */

    public ScObserver(Object toObserve) {
        // Hold and init
        this.mToObserve = toObserve;
        this.init();
    }


    /****************************************************************************************
     * Private methods
     */

    /**
     * Get all accessible methods from a passed class reference.
     *
     * @param source the class
     * @return the methods list
     */
    private List<Method> getAccessibleMethods(Class source) {
        // The holder
        List<Method> methods = new ArrayList<>();

        // Cycle for the inherited classes tree
        while (source != null) {
            // Cycle for all methods inside the current class source
            for (Method method : source.getDeclaredMethods()) {
                // Check if callable by out
                int modifiers = method.getModifiers();
                if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                    methods.add(method);
                }
            }
            // Move to the parent
            source = source.getSuperclass();
        }
        // Return
        return methods;
    }

    /**
     * Filters the methods list and hold only when no have parameters to pass and return some
     * values.
     *
     * @param methods the original methods list
     */
    private void filtersForCallableMethods(List<Method> methods) {
        // To delete
        List<Method> toDelete = new ArrayList<>();

        // Cycle all methods and check the number of parameters when calling or return void.
        // If have add this to the toDelete list.
        for (Method method : methods) {
            if (method.getParameterTypes().length > 0 ||
                    method.getReturnType().getName().equals("void")) {
                toDelete.add(method);
            }
        }

        // Remove all not proper methods
        methods.removeAll(toDelete);
    }

    /**
     * Invoke a method without params.
     *
     * @param method the method to invoke
     * @return the method result
     */
    private Object invoke(Method method) {
        try {
            // Invoke the method and store the result if possible
            return method.invoke(this.mToObserve);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Call all methods in list and save the result and the method name inside an HashTable.
     *
     * @param methods the methods list
     * @return the current status
     */
    private HashMap<Method, Object> getCurrentStatus(List<Method> methods) {
        // Holder
        HashMap<Method, Object> results = new HashMap<>();

        // Cycle all methods
        for (Method method : methods) {
            // Invoke the method and store the result if possible
            results.put(method, this.invoke(method));
        }

        // Return the results
        return results;
    }

    /**
     * Init the class
     */
    private void init() {
        // Check for empty value
        if (this.mToObserve == null)
            return;

        // Get all available methods from the class to observe
        List<Method> methods = this.getAccessibleMethods(this.mToObserve.getClass());
        this.filtersForCallableMethods(methods);

        // Store the current class status
        this.mStatus = this.getCurrentStatus(methods);
    }


    /****************************************************************************************
     * Public methods
     */

    /**
     * Check if have some changes and update the current status.
     *
     * @return true if have changes
     */
    @SuppressWarnings("unused")
    public boolean isChanged() {
        // Check for empty values
        if (this.mStatus == null)
            return false;

        // Set the initial trigger status and cycle all HashTable methods
        boolean changed = false;
        for (Method method : this.mStatus.keySet()) {
            // Invoke the method and store the new/old results
            Object newResult = this.invoke(method);
            Object oldResult = this.mStatus.get(method);

            // Compare
            if (!(newResult == null && oldResult == null) &&
                    (newResult != null && !newResult.equals(oldResult))) {
                // Set the trigger as changed and fix the new value
                changed = true;
                this.mStatus.put(method, newResult);
            }
        }

        // Return the result
        return changed;
    }

    /**
     * Exclude the methods passed from checking
     *
     * @param methodsName the method name to exclude
     */
    //
    @SuppressWarnings("unused")
    public void exclude(String... methodsName) {
        // To list
        List<String> list = Arrays.asList(methodsName);
        List<Method> toRemove = new ArrayList<>();

        // Cycle all methods
        for (Method method : this.mStatus.keySet()) {
            // Check if the list contain the current method name
            if (list.contains(method.getName())) {
                // If found name correspondence add to remove list
                toRemove.add(method);
            }
        }

        // Remove all filtered methods
        this.mStatus.keySet().removeAll(toRemove);
    }

}
