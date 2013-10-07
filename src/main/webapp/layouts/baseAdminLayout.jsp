<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
  </head>
  <body>
        <table  height="800" width="100%" border="1" cellspacing="20" cellpadding="10">
      <tr>
        <td height="30" colspan="2">
          <tiles:insertAttribute name="header" />
        </td>
      </tr>
      <tr height="600">
        <td valign="top" width="350">
          <tiles:insertAttribute name="menu" />
        </td>
        <td valign="top">
          <tiles:insertAttribute name="body" />
        </td>
      </tr>
    </table>
  </body>
</html>