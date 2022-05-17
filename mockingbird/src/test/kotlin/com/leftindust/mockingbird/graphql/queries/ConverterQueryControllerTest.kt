package com.leftindust.mockingbird.graphql.queries

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConverterQueryControllerTest {

    @Test
    fun convert() {
        val inputJson = """
            |[
            |   {pid: 1, first_name: "marcus", cell_phone: {number: 123422, phoneType: Cell }},
            |   {pid: 2, first_name: "dan", cell_phone: {number: 12121, phoneType: Home }}
            |]
            |""".trimMargin()
        val expectedCsv = """
            |pid,first_name,cell_phone.number,cell_phone.phoneType
            |1,"marcus",123422,"Cell"
            |2,"dan",12121,"Home"
            |""".trimMargin()
        val result = ConverterQueryController().convert(inputJson, ConverterQueryController.ConvertTarget.Csv)
        assertEquals(expectedCsv, result)
    }

    @Test
    fun `convert with nulls`() {
        val inputJson = """
            |[
            |   {pid: 2, first_name: "dan", middle_name: "the man" , cell_phone: {number: 12121, phoneType: Home }},
            |   {pid: 1, first_name: "marcus", cell_phone: {number: 123422, phoneType: Cell }}
            |]
            |""".trimMargin()
        val expectedCsv = """
            |pid,first_name,middle_name,cell_phone.number,cell_phone.phoneType
            |2,"dan","the man",12121,"Home"
            |1,"marcus",null,123422,"Cell"
            |""".trimMargin()
        val result = ConverterQueryController().convert(inputJson, ConverterQueryController.ConvertTarget.Csv)
        assertEquals(expectedCsv, result)
    }


    @Test
    fun `convert with arrays`() {
        val inputJson = """
            |[
            |   {pid: 2, first_name: "dan", diseases: ["covid", "cancer", "ebola"]},
            |   {pid: 1, first_name: "marcus", diseases: ["e-coli"]}
            |]
            |""".trimMargin()
        val expectedCsv = """
            |pid,first_name,diseases[0],diseases[1],diseases[2]
            |2,"dan","covid","cancer","ebola"
            |1,"marcus","e-coli",null,null
            |""".trimMargin()
        val result = ConverterQueryController().convert(inputJson, ConverterQueryController.ConvertTarget.Csv)
        assertEquals(expectedCsv, result)
    }
}