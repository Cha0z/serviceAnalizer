
package com.borodin.service_anylizer.rql.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RQLParser}.
 */
public interface RQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RQLParser#log}.
	 * @param ctx the parse tree
	 */
	void enterLog(RQLParser.LogContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#log}.
	 * @param ctx the parse tree
	 */
	void exitLog(RQLParser.LogContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(RQLParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(RQLParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#ip}.
	 * @param ctx the parse tree
	 */
	void enterIp(RQLParser.IpContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#ip}.
	 * @param ctx the parse tree
	 */
	void exitIp(RQLParser.IpContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#dateAndTime}.
	 * @param ctx the parse tree
	 */
	void enterDateAndTime(RQLParser.DateAndTimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#dateAndTime}.
	 * @param ctx the parse tree
	 */
	void exitDateAndTime(RQLParser.DateAndTimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(RQLParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(RQLParser.DateContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#time}.
	 * @param ctx the parse tree
	 */
	void enterTime(RQLParser.TimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#time}.
	 * @param ctx the parse tree
	 */
	void exitTime(RQLParser.TimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#hour}.
	 * @param ctx the parse tree
	 */
	void enterHour(RQLParser.HourContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#hour}.
	 * @param ctx the parse tree
	 */
	void exitHour(RQLParser.HourContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#minute}.
	 * @param ctx the parse tree
	 */
	void enterMinute(RQLParser.MinuteContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#minute}.
	 * @param ctx the parse tree
	 */
	void exitMinute(RQLParser.MinuteContext ctx);
	/**
	 * Enter a parse tree produced by {@link RQLParser#second}.
	 * @param ctx the parse tree
	 */
	void enterSecond(RQLParser.SecondContext ctx);
	/**
	 * Exit a parse tree produced by {@link RQLParser#second}.
	 * @param ctx the parse tree
	 */
	void exitSecond(RQLParser.SecondContext ctx);
}