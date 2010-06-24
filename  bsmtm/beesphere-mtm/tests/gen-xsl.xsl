<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:exps="http://www.beesphere.net/xsds/2010/txt"
	xmlns:txt="http://www.beesphere.net/xsds/2010/txt">
	<xsl:output indent="yes" />
	<xsl:param name="com.soa.defaults.requestDate" />
	<xsl:param name="com.beesphere.io.adapters.currentDate" />
	<xsl:param name="com.soa.defaults.remoteUser" />
	<xsl:param name="NOW" />
	<xsl:variable name="v1"
		select="concat('Employment', '-',current-date())" />
	<xsl:variable name="myVar" select="$com.soa.defaults.requestDate" />
	<xsl:template match="/">
		<xsl:element name="exps:data">
			<xsl:element name="exps:rs">
				<xsl:for-each select="/root/in1/data/rs/r">
					<xsl:element name="exps:r">
						<xsl:variable name="cell1"
							select="current()/c_2" />
						<xsl:variable name="v3" select="$NOW" />
						<xsl:element name="exps:c_0">
							<xsl:value-of select="$v1" />
						</xsl:element>
						<xsl:element name="exps:c_1">
							<xsl:value-of select="$cell1" />
						</xsl:element>
						<xsl:element name="exps:c_2">
							<xsl:value-of select="$v3" />
						</xsl:element>
						<xsl:element name="exps:c_3">
							<xsl:value-of select="$myVar" />
						</xsl:element>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>