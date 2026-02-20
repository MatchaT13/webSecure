<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/page">
        <html>
            <head>
                <title><xsl:value-of select="@title"/></title>
            </head>
            <body>
                <h1><xsl:value-of select="@title"/></h1>
                <p><xsl:value-of select="content"/></p>

                <ul>
                    <xsl:for-each select="link">
                        <li>
                            <a href="ContentServlet?page={@to}">
                                <xsl:value-of select="."/>
                            </a>
                        </li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
