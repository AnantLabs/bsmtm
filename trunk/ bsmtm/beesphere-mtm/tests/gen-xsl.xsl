<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:json="http://www.beesphere.net/xsds/2010/json"
	xmlns:txt="http://www.beesphere.net/xsds/2010/txt">
	<xsl:output indent="yes" />
	<xsl:param name="com.soa.defaults.requestDate" />
	<xsl:param name="com.beesphere.io.adapters.currentDate" />
	<xsl:param name="com.soa.defaults.remoteUser" />
	<xsl:variable name="v1" select="true" />
	<xsl:variable name="v2" select="/root/re61/data/rs/r/c_3" />
	<xsl:variable name="v3">true</xsl:variable>
	<xsl:template match="/">
		<xsl:element name="json:data">
			<xsl:element name="json:rs">
				<xsl:attribute name="json:isArr"><xsl:value-of
						select="$v1" />
				</xsl:attribute>
				<xsl:for-each select="/root/re61/data/rs/r">
					<xsl:element name="json:rs_r">
						<xsl:element name="json:c_0">
							<xsl:value-of select="c_0" />
						</xsl:element>
						<xsl:element name="json:c_1">
							<xsl:value-of select="c_1" />
						</xsl:element>
						<xsl:element name="json:c_2">
							<xsl:value-of select="c_2" />
						</xsl:element>
						<xsl:element name="json:c_3">
							<xsl:value-of select="$v2" />
						</xsl:element>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>