package org.acme.example.application.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.acme", importOptions = ImportOption.DoNotIncludeTests.class)
class SlicesIsolationTest {

    @ArchTest
    static final ArchRule PACKAGES_SHOULD_BE_FREE_OF_CYCLES = SlicesRuleDefinition
            .slices().matching("..(*)..")
            .should().beFreeOfCycles()
            .because("code should not contain cyclic dependencies");

    @ArchTest
    static final ArchRule SLICES_SHOULD_NOT_DEPEND_ON_EACH_OTHER = SlicesRuleDefinition
            .slices().matching("org.acme.example.(*)..")
            .should().notDependOnEachOther()
            .because("functional slices should be independent");

    @ArchTest
    static final ArchRule UTILS_SHOULD_NOT_DEPEND_ON_APP = layeredArchitecture()
            .layer("Utilities").definedBy("org.acme.util..")
            .layer("Application").definedBy("org.acme.example..")
            .whereLayer("Utilities").mayOnlyBeAccessedByLayers("Application")
            .whereLayer("Application").mayNotBeAccessedByAnyLayer()
            .because("utilities should not depend on applications");
}
