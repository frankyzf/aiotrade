package ru.circumflex.orm

import ru.circumflex.core.Circumflex
import ru.circumflex.orm.Query.QueryHelper

/**
 * Aggregates all ORM-related interfaces into configuration object.
 * You may want to provide your own implementation of all these methods
 * if you are not satisfied with default ones.
 */
object ORM extends QueryHelper {

  private var _cachedDialect: Dialect = null

  /**
   * Returns connection provider.
   * Can be overriden with "orm.connectionProvider" configuration parameter.
   */
  val connectionProvider: ConnectionProvider = Circumflex.cfg("orm.connectionProvider") match {
    case Some(p: ConnectionProvider) => p
    case Some(c: Class[ConnectionProvider]) => c.newInstance
    case Some(s: String) => Class
      .forName(s, true, Circumflex.classLoader)
      .newInstance
      .asInstanceOf[ConnectionProvider]
    case _ => ConnectionProvider.Default
  }

  /**
   * Returns SQL dialect.
   * Can be overriden with "orm.dialect" configuration parameter.
   */
  val dialect: Dialect = Circumflex.cfg("orm.dialect") match {
    case Some(d: Dialect) => d
    case Some(c: Class[Dialect]) => c.newInstance
    case Some(s: String) => {
        if (_cachedDialect == null)
          _cachedDialect = Class.forName(s, true, Circumflex.classLoader)
        .newInstance
        .asInstanceOf[Dialect]
        _cachedDialect
      }
    case _ => DefaultDialect
  }

  /**
   * Returns SQL type converter.
   * Can be overriden with "orm.typeConverter" configuration parameter.
   */
  val typeConverter: TypeConverter = Circumflex.cfg("orm.typeConverter") match {
    case Some(tc: TypeConverter) => tc
    case Some(c: Class[TypeConverter]) => c.newInstance
    case Some(s: String) => Class.forName(s, true, Circumflex.classLoader)
      .newInstance
      .asInstanceOf[TypeConverter]
    case _ => TypeConverter.Default
  }

  val defaultSchemaName = Circumflex.cfg("orm.defaultSchema") match {
    case Some(s: String) => s
    case _ => "public"
  }

  val transactionManager: TransactionManager = Circumflex.cfg("orm.transactionManager") match {
    case Some(tm: TransactionManager) => tm
    case Some(c: Class[TransactionManager]) => c.newInstance
    case Some(s: String) => Class.forName(s, true, Circumflex.classLoader)
      .newInstance
      .asInstanceOf[TransactionManager]
    case _ => TransactionManager.Default
  }

  def tx = transactionManager.getTransaction

}