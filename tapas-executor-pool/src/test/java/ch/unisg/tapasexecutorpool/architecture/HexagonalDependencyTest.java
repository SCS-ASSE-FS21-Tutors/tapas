package ch.unisg.tapasexecutorpool.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class HexagonalDependencyTest {

    JavaClasses importedClasses = new ClassFileImporter().importPackages("ch.unisg..");

    /**
     * Checks that domain classes don't depend on any outer layer
     */
    @Test
    public void checkDomainDependencies() {

        noClasses().that().resideInAnyPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage("..application..", "..adapter..")
                .check(importedClasses);
    }

    /**
     * Checks that no class from the application (services, use cases) depend on
     * any classes in the adapter package (controllers, repos)
     */
    @Test
    public void checkApplicationDependencies() {

        noClasses().that().resideInAnyPackage("..application..")
                .should().dependOnClassesThat().resideInAnyPackage("..adapter..")
                .check(importedClasses);
    }
}
