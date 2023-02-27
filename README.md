OBridge
=======

[![PayPal donate button](http://img.shields.io/paypal/donate.png?color=yellow)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=QQAFXN8GH5YFN&lc=GB&item_name=OBridge&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted "Help OBridge development using Paypal")  


OBridge provides a simple Java source code generator for calling Oracle PL/SQL package procedures.

Supported input, output parameters and return values are:
  * BINARY_INTEGER
  * BOOLEAN
  * CHAR
  * CLOB
  * BLOB
  * DATE
  * NCHAR
  * NUMBER
  * NVARCHAR2
  * OBJECT - Oracle Object Type
  * PLS_INTEGER
  * TABLE - Table of Oracle Object Type
  * TIMESTAMP
  * VARCHAR2
  * RAW
  
The following types cannot be implemented, because JDBC driver does not supports them:
  * Types declared in source code
  * %ROWTYPE parameters
  
Generated code compiles with Java 17.

Usage
-----

Download the latest version from [releases](https://github.com/karsany/obridge/releases).

After downloading, create a `yml` or `properties` configuration file:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:EXAMPLE/EXAMPLE@//10.0.5.1:1521/EXAMPLE

obridge:
  # Defaults
  generate-nested-types: true
  packages:
    converter-objects: converters
    procedure-context-objects: context
    package-objects: packages
    entity-objects: objects
  logging:
    initializer: private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(%s.class.getName());
    method: LOGGER.info
  # Required
  source-root: ./src/main/java
  root-package-name: org.obridge.generated
  includes:
    - owner: PACKAGE_OWNER
      name: PK_EXAMPLE
```
Run the generator:

	java -jar obridge.jar
		
OBridge connects to the specified database and generates the required classes.

Calling a PL/SQL procedure
--------------------------

For example you have the following PL/SQL code:

```sql
Create Or Replace Package simple_procedures is
  Function simple_func(a In Varchar2,
					   b In Out Varchar2,
					   c Out Varchar2) Return Number;
End simple_procedures;
```
Generated source:

```java
public class SimpleProcedures {
	public static SimpleProceduresSimpleFunc simpleFunc(String a, String b,  Connection connection) throws SQLException { ... }
}
```
You can call the SimpleProcedures.simpleFunc method:

```java
SimpleProceduresSimpleFunc ret = SimpleProcedures.simpleFunc("hello", "world", conn); // conn is the database connection
```
Return object will hold the input and output parameters, converted to Java types.
```java
public class SimpleProceduresSimpleFunc {

	private BigDecimal functionReturn;
	private String a;
	private String b;
	private String c;
	
	// getters, setters

}
```
