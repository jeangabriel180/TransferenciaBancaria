package com.jean.gabriel.transferenciaBancaria;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features"},
        glue = {"com.jean.gabriel.transferenciaBancaria"},
        plugin = {"json:target/cucumber-reports/cucumber.json"})
public class RunTransferenciaBancariaTest {
}
