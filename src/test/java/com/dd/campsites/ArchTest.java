package com.dd.campsites;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.dd.campsites");

        noClasses()
            .that()
            .resideInAnyPackage("com.dd.campsites.service..")
            .or()
            .resideInAnyPackage("com.dd.campsites.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.dd.campsites.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
