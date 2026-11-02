package com.embobinados.apugen_backend.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class CustomFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
                .registerPattern(
                        "normalizar",
                        "unaccent(lower(?1))",
                        functionContributions.getTypeConfiguration()
                                .getBasicTypeRegistry()
                                .resolve(StandardBasicTypes.STRING)
                );
    }
}