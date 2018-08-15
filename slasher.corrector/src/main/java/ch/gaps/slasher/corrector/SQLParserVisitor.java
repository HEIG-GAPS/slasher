// Generated from /Users/Sashok/Documents/2017-2018/Stage/slasher/slasher.corrector/src/main/antlr4/ch/gaps/slasher/SQLParser.g4 by ANTLR 4.7
package ch.gaps.slasher.corrector;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SQLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SQLParser#sql}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSql(SQLParser.SqlContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SQLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#data_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData_statement(SQLParser.Data_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#data_change_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData_change_statement(SQLParser.Data_change_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#schema_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema_statement(SQLParser.Schema_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#index_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex_statement(SQLParser.Index_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#create_table_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreate_table_statement(SQLParser.Create_table_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_elements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_elements(SQLParser.Table_elementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#field_element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField_element(SQLParser.Field_elementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#field_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField_type(SQLParser.Field_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#param_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_clause(SQLParser.Param_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(SQLParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#method_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod_specifier(SQLParser.Method_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_space_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_space_specifier(SQLParser.Table_space_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_space_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_space_name(SQLParser.Table_space_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_partitioning_clauses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_partitioning_clauses(SQLParser.Table_partitioning_clausesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#range_partitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange_partitions(SQLParser.Range_partitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#range_value_clause_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange_value_clause_list(SQLParser.Range_value_clause_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#range_value_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange_value_clause(SQLParser.Range_value_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#hash_partitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHash_partitions(SQLParser.Hash_partitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#individual_hash_partitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndividual_hash_partitions(SQLParser.Individual_hash_partitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#individual_hash_partition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndividual_hash_partition(SQLParser.Individual_hash_partitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#hash_partitions_by_quantity}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHash_partitions_by_quantity(SQLParser.Hash_partitions_by_quantityContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#list_partitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_partitions(SQLParser.List_partitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#list_value_clause_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_value_clause_list(SQLParser.List_value_clause_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#list_value_partition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_value_partition(SQLParser.List_value_partitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#column_partitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_partitions(SQLParser.Column_partitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#partition_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartition_name(SQLParser.Partition_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#drop_table_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDrop_table_statement(SQLParser.Drop_table_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(SQLParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#nonreserved_keywords}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonreserved_keywords(SQLParser.Nonreserved_keywordsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#unsigned_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsigned_literal(SQLParser.Unsigned_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#general_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeneral_literal(SQLParser.General_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#datetime_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetime_literal(SQLParser.Datetime_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#time_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime_literal(SQLParser.Time_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#timestamp_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimestamp_literal(SQLParser.Timestamp_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#date_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_literal(SQLParser.Date_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#boolean_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_literal(SQLParser.Boolean_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#data_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData_type(SQLParser.Data_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#predefined_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredefined_type(SQLParser.Predefined_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#network_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNetwork_type(SQLParser.Network_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#character_string_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter_string_type(SQLParser.Character_string_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#type_length}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_length(SQLParser.Type_lengthContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#national_character_string_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNational_character_string_type(SQLParser.National_character_string_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#binary_large_object_string_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary_large_object_string_type(SQLParser.Binary_large_object_string_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#numeric_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeric_type(SQLParser.Numeric_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#exact_numeric_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExact_numeric_type(SQLParser.Exact_numeric_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#approximate_numeric_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApproximate_numeric_type(SQLParser.Approximate_numeric_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#precision_param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrecision_param(SQLParser.Precision_paramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#boolean_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_type(SQLParser.Boolean_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#datetime_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetime_type(SQLParser.Datetime_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#bit_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBit_type(SQLParser.Bit_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#binary_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary_type(SQLParser.Binary_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#value_expression_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue_expression_primary(SQLParser.Value_expression_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#parenthesized_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesized_value_expression(SQLParser.Parenthesized_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#nonparenthesized_value_expression_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonparenthesized_value_expression_primary(SQLParser.Nonparenthesized_value_expression_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#unsigned_value_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsigned_value_specification(SQLParser.Unsigned_value_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#unsigned_numeric_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsigned_numeric_literal(SQLParser.Unsigned_numeric_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#signed_numerical_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigned_numerical_literal(SQLParser.Signed_numerical_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#set_function_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet_function_specification(SQLParser.Set_function_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#aggregate_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAggregate_function(SQLParser.Aggregate_functionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#general_set_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeneral_set_function(SQLParser.General_set_functionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#set_function_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet_function_type(SQLParser.Set_function_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#filter_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilter_clause(SQLParser.Filter_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#grouping_operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrouping_operation(SQLParser.Grouping_operationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#case_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_expression(SQLParser.Case_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#case_abbreviation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_abbreviation(SQLParser.Case_abbreviationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#case_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_specification(SQLParser.Case_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#simple_case}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_case(SQLParser.Simple_caseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#searched_case}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearched_case(SQLParser.Searched_caseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#simple_when_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_when_clause(SQLParser.Simple_when_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#searched_when_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearched_when_clause(SQLParser.Searched_when_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#else_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_clause(SQLParser.Else_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResult(SQLParser.ResultContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#cast_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCast_specification(SQLParser.Cast_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#cast_operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCast_operand(SQLParser.Cast_operandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#cast_target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCast_target(SQLParser.Cast_targetContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue_expression(SQLParser.Value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#common_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommon_value_expression(SQLParser.Common_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#numeric_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeric_value_expression(SQLParser.Numeric_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(SQLParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(SQLParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(SQLParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#numeric_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeric_primary(SQLParser.Numeric_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#sign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSign(SQLParser.SignContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#numeric_value_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeric_value_function(SQLParser.Numeric_value_functionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#extract_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtract_expression(SQLParser.Extract_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#extract_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtract_field(SQLParser.Extract_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#time_zone_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime_zone_field(SQLParser.Time_zone_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#extract_source}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtract_source(SQLParser.Extract_sourceContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#string_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_value_expression(SQLParser.String_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#character_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter_value_expression(SQLParser.Character_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#character_factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter_factor(SQLParser.Character_factorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#character_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter_primary(SQLParser.Character_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#string_value_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_value_function(SQLParser.String_value_functionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#trim_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrim_function(SQLParser.Trim_functionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#trim_operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrim_operands(SQLParser.Trim_operandsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#trim_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrim_specification(SQLParser.Trim_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#boolean_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_value_expression(SQLParser.Boolean_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#or_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_predicate(SQLParser.Or_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#and_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_predicate(SQLParser.And_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#boolean_factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_factor(SQLParser.Boolean_factorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#boolean_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_test(SQLParser.Boolean_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#is_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIs_clause(SQLParser.Is_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#truth_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTruth_value(SQLParser.Truth_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#boolean_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_primary(SQLParser.Boolean_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#boolean_predicand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_predicand(SQLParser.Boolean_predicandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#parenthesized_boolean_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesized_boolean_value_expression(SQLParser.Parenthesized_boolean_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#row_value_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRow_value_expression(SQLParser.Row_value_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#row_value_special_case}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRow_value_special_case(SQLParser.Row_value_special_caseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#explicit_row_value_constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicit_row_value_constructor(SQLParser.Explicit_row_value_constructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#row_value_predicand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRow_value_predicand(SQLParser.Row_value_predicandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#row_value_constructor_predicand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRow_value_constructor_predicand(SQLParser.Row_value_constructor_predicandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_expression(SQLParser.Table_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#from_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrom_clause(SQLParser.From_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_reference_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_reference_list(SQLParser.Table_reference_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_reference(SQLParser.Table_referenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#joined_table}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoined_table(SQLParser.Joined_tableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#joined_table_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoined_table_primary(SQLParser.Joined_table_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#cross_join}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCross_join(SQLParser.Cross_joinContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#qualified_join}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualified_join(SQLParser.Qualified_joinContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#natural_join}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNatural_join(SQLParser.Natural_joinContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#union_join}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion_join(SQLParser.Union_joinContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#join_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_type(SQLParser.Join_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#outer_join_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOuter_join_type(SQLParser.Outer_join_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#outer_join_type_part2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOuter_join_type_part2(SQLParser.Outer_join_type_part2Context ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#join_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_specification(SQLParser.Join_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#join_condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_condition(SQLParser.Join_conditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#named_columns_join}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamed_columns_join(SQLParser.Named_columns_joinContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_primary(SQLParser.Table_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#column_name_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_name_list(SQLParser.Column_name_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#derived_table}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDerived_table(SQLParser.Derived_tableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#where_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhere_clause(SQLParser.Where_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#search_condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearch_condition(SQLParser.Search_conditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#groupby_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupby_clause(SQLParser.Groupby_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#grouping_element_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrouping_element_list(SQLParser.Grouping_element_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#grouping_element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrouping_element(SQLParser.Grouping_elementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#ordinary_grouping_set}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinary_grouping_set(SQLParser.Ordinary_grouping_setContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#ordinary_grouping_set_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinary_grouping_set_list(SQLParser.Ordinary_grouping_set_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#rollup_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRollup_list(SQLParser.Rollup_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#cube_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCube_list(SQLParser.Cube_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#empty_grouping_set}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmpty_grouping_set(SQLParser.Empty_grouping_setContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#having_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHaving_clause(SQLParser.Having_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#row_value_predicand_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRow_value_predicand_list(SQLParser.Row_value_predicand_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#query_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery_expression(SQLParser.Query_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#query_expression_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery_expression_body(SQLParser.Query_expression_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#non_join_query_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNon_join_query_expression(SQLParser.Non_join_query_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#query_term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery_term(SQLParser.Query_termContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#non_join_query_term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNon_join_query_term(SQLParser.Non_join_query_termContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#query_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery_primary(SQLParser.Query_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#non_join_query_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNon_join_query_primary(SQLParser.Non_join_query_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#simple_table}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_table(SQLParser.Simple_tableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#explicit_table}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicit_table(SQLParser.Explicit_tableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_or_query_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_or_query_name(SQLParser.Table_or_query_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_name(SQLParser.Table_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#query_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery_specification(SQLParser.Query_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#select_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect_list(SQLParser.Select_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#select_sublist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect_sublist(SQLParser.Select_sublistContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#derived_column}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDerived_column(SQLParser.Derived_columnContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#qualified_asterisk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualified_asterisk(SQLParser.Qualified_asteriskContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#set_qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet_qualifier(SQLParser.Set_qualifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#column_reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_reference(SQLParser.Column_referenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#as_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAs_clause(SQLParser.As_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#column_reference_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_reference_list(SQLParser.Column_reference_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#scalar_subquery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalar_subquery(SQLParser.Scalar_subqueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#row_subquery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRow_subquery(SQLParser.Row_subqueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#table_subquery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_subquery(SQLParser.Table_subqueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#subquery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubquery(SQLParser.SubqueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(SQLParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#comparison_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison_predicate(SQLParser.Comparison_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#comp_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_op(SQLParser.Comp_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#between_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetween_predicate(SQLParser.Between_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#between_predicate_part_2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetween_predicate_part_2(SQLParser.Between_predicate_part_2Context ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#in_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIn_predicate(SQLParser.In_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#in_predicate_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIn_predicate_value(SQLParser.In_predicate_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#in_value_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIn_value_list(SQLParser.In_value_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#pattern_matching_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern_matching_predicate(SQLParser.Pattern_matching_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#pattern_matcher}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern_matcher(SQLParser.Pattern_matcherContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#negativable_matcher}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegativable_matcher(SQLParser.Negativable_matcherContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#regex_matcher}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegex_matcher(SQLParser.Regex_matcherContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#null_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNull_predicate(SQLParser.Null_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#quantified_comparison_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantified_comparison_predicate(SQLParser.Quantified_comparison_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#quantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifier(SQLParser.QuantifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#all}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAll(SQLParser.AllContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#some}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSome(SQLParser.SomeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#exists_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExists_predicate(SQLParser.Exists_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#unique_predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnique_predicate(SQLParser.Unique_predicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#primary_datetime_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_datetime_field(SQLParser.Primary_datetime_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#non_second_primary_datetime_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNon_second_primary_datetime_field(SQLParser.Non_second_primary_datetime_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#extended_datetime_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtended_datetime_field(SQLParser.Extended_datetime_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#routine_invocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoutine_invocation(SQLParser.Routine_invocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#function_names_for_reserved_words}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_names_for_reserved_words(SQLParser.Function_names_for_reserved_wordsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#function_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_name(SQLParser.Function_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#sql_argument_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSql_argument_list(SQLParser.Sql_argument_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#orderby_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderby_clause(SQLParser.Orderby_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#sort_specifier_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSort_specifier_list(SQLParser.Sort_specifier_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#sort_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSort_specifier(SQLParser.Sort_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#order_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrder_specification(SQLParser.Order_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#limit_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimit_clause(SQLParser.Limit_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#null_ordering}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNull_ordering(SQLParser.Null_orderingContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#insert_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsert_statement(SQLParser.Insert_statementContext ctx);
}