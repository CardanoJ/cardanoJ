package com.cardanoj.annotation.processor;

import com.cardanoj.annotation.processor.model.ClassDefinition;
import com.squareup.javapoet.TypeSpec;

/**
 * Interface for code generator
 */
public interface CodeGenerator {
    TypeSpec generate(ClassDefinition classDefinition);
}
