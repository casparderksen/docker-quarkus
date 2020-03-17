package org.acme.example.application.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "org.acme", importOptions = ImportOption.DoNotIncludeTests.class)
class OnionArchitectureTest {

    @ArchTest
    static final ArchRule ONION_ARCHITECTURE_IS_RESPECTED = onionArchitecture()
            .domainModels("..domain.model..")
            .domainServices("..domain.service..")
            .applicationServices("..application..")
            .adapter("persistence", "..adapter.persistence..")
            .adapter("rest", "..adapter.rest..")
            .because("code should have Onion Architecture structure");
}