package mye030.advanceddb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "countries")
public class Countries {
	
	@Id
	@Column("ISO_Code")
    private int iso_Code;// int,
	
	@Column("ISO")
	private String iso; //varchar(3),
	
	@Column( "ISO3")
    private String iso3; //varchar(3),
	
	
	@Column("FIPS")
    private String fips;// varchar(255),
	
	@Column("Display_Name")
    private String displayName;// varchar(255),
	
	@Column("Official_Name")
    private String officialName;// varchar(255),
	
	@Column("Capital")
    private String capital;// varchar(255),
	
	@Column("Continent")
    private String continent;//varchar(255),
	
	@Column("Currency_Code")
    private String currencyCode;
	
	@Column("Currency_name")
    private String currencyName;
	
	@Column("Phone")
    private String phone;
	
	@Column("Region_Code")
    private String regionCode;
	
	@Column("Region_Name")
    private String regionName;
	
	@Column("Subregion_Code")
    private String subregionCode;
	
	@Column("Subregion_Name")
    private String subregionName;
	
	@Column("Intermediate_Region_Code")
    private String intermediateRegionCode;
	
	@Column("Intermediate_Region_Name")
    private String intermediateRegionName;
	
	@Column("Status_of_Country")
    private String statusofCountry;
	
	@Column("Developed_or_not")
    private String developedOrNot;
	
	@Column("SIDS")
    private String sids;
	
	@Column("LLDC")
    private String lldc;
	
	@Column( "LDC")
    private String ldc;
	
	@Column( "Area_SqKm")
    private int areaSqKm ;
	
	@Column( "Population")
    private int population;

	public String getIso() {
		return iso;
	}

	public String getIso3() {
		return iso3;
	}

	public int getIso_Code() {
		return iso_Code;
	}

	public String getFips() {
		return fips;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getOfficialName() {
		return officialName;
	}

	public String getCapital() {
		return capital;
	}

	public String getContinent() {
		return continent;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public String getPhone() {
		return phone;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getSubregionCode() {
		return subregionCode;
	}

	public String getSubregionName() {
		return subregionName;
	}

	public String getIntermediateRegionCode() {
		return intermediateRegionCode;
	}

	public String getIntermediateRegionName() {
		return intermediateRegionName;
	}

	public String getStatusofCountry() {
		return statusofCountry;
	}

	public String getDevelopedOrNot() {
		return developedOrNot;
	}

	public String getSids() {
		return sids;
	}

	public String getLldc() {
		return lldc;
	}

	public String getLdc() {
		return ldc;
	}

	public int getAreaSqKm() {
		return areaSqKm;
	}

	public int getPopulation() {
		return population;
	}

}