package models_vivi;

import java.util.List;

//model containing all data for final excel file
//only used for multi api call
//single calls dont need this
public class ApiMultiCallModel {
	
	public List<String> randomInfo;
	public String issn;
	public String eIssn;
	public String title;
	public String submitted;
	public String accepted;
	public String published;	
	
	public String submittedPrerequisites;
	public String acceptedPrerequisites;
	public String publishedPrerequisites;
	
	public String submittedCopyRightOwner;
	public String acceptedCopyRightOwner;
	public String publishedCopyRightOwner;
	
	public String submittedLicense;
	public String acceptedLicense;
	public String publishedLicense;
	
	public String submittedAdditionalOAFees;
	public String acceptedAdditionalOAFees;
	public String publishedAdditionalOAFees;
	
	public String submittedRepository;
	public String acceptedRepository;
	public String publishedRepository;
	
	public String submittedEmbargo;
	public String acceptedEmbargo;
	public String publishedEmbargo;
	
	public String submittedConditions;
	public String acceptedConditions;
	public String publishedConditions;
	
	public String submittedUrl;
	public String acceptedUrl;
	public String publishedUrl;
	
	public String lastUpdated;	
	public String publisherType;
	public String publisher;
	
	public String urls;
	
	//constructor; extended version; Individual Conditions stored in separate properties
	public ApiMultiCallModel(String issn, 
			String eIssn, 
			String title, 			 
			String submitted, 
			String accepted, 
			String published, 
			
			String submittedPrerequisites,
			String submittedCopyRightOwner,
			String submittedAdditionalOAFees,
			String submittedRepository,
			String submittedEmbargo,
			String submittedConditions,			
			
			String acceptedPrerequisites,
			String acceptedCopyRightOwner,
			String acceptedAdditionalOAFees,
			String acceptedRepository,
			String acceptedEmbargo,
			String acceptedConditions,			
			
			String publishedPrerequisites,
			String publishedCopyRightOwner,
			String publishedAdditionalOAFees,
			String publishedRepository,
			String publishedEmbargo,
			String publishedConditions,	
			
			String lastUpdated, 
			String publisherType, 
			String publisher, 
			
			String submittedLicense,
			String acceptedLicense,
			String publishedLicense,
			
			String urls,
			
			List<String> randomInfo)
	{		
		this.issn = issn;
		this.eIssn = eIssn;
		this.title = title;
		
		this.submitted = submitted;
		this.accepted = accepted;
		this.published = published;
		
		this.submittedPrerequisites = submittedPrerequisites;
		this.acceptedPrerequisites = acceptedPrerequisites;
		this.publishedPrerequisites = publishedPrerequisites;
		
		this.submittedLicense = submittedLicense;
		this.acceptedLicense = acceptedLicense;
		this.publishedLicense = publishedLicense;		
		
		this.submittedCopyRightOwner = submittedCopyRightOwner;
		this.acceptedCopyRightOwner = acceptedCopyRightOwner;
		this.publishedCopyRightOwner = publishedCopyRightOwner;
		
		this.submittedAdditionalOAFees = submittedAdditionalOAFees;
		this.acceptedAdditionalOAFees = acceptedAdditionalOAFees;
		this.publishedAdditionalOAFees = publishedAdditionalOAFees;
		
		this.submittedRepository = submittedRepository;
		this.acceptedRepository = acceptedRepository;
		this.publishedRepository = publishedRepository;
		
		this.submittedEmbargo = submittedEmbargo;
		this.acceptedEmbargo = acceptedEmbargo;
		this.publishedEmbargo = publishedEmbargo;
		
		this.submittedConditions = submittedConditions;
		this.acceptedConditions = acceptedConditions;
		this.publishedConditions = publishedConditions;		
	
		this.lastUpdated = lastUpdated;
		this.publisherType = publisherType;
		this.publisher = publisher;
		
		this.urls = urls;
		
		this.randomInfo = randomInfo;	
		
	
	}	
	
	//constructor; No ISSN found, search via title deactivated: create empty model
		public ApiMultiCallModel(String issn_not_found_message,
				String title,				
				List<String> randomInfo)
		{		
			this.issn = issn_not_found_message;
			this.eIssn = "";
			this.title = title;
			
			this.submitted = "";
			this.accepted = "";
			this.published = "";
			
			this.submittedPrerequisites = "";
			this.acceptedPrerequisites = "";
			this.publishedPrerequisites = "";
			
			this.submittedLicense = "";
			this.acceptedLicense = "";
			this.publishedLicense = "";
			
			this.submittedCopyRightOwner = "";
			this.acceptedCopyRightOwner = "";
			this.publishedCopyRightOwner = "";
			
			this.submittedAdditionalOAFees = "";
			this.acceptedAdditionalOAFees = "";
			this.publishedAdditionalOAFees = "";
			
			this.submittedRepository = "";
			this.acceptedRepository = "";
			this.publishedRepository = "";
			
			this.submittedEmbargo = "";
			this.acceptedEmbargo = "";
			this.publishedEmbargo = "";
			
			this.submittedConditions = "";
			this.acceptedConditions = "";
			this.publishedConditions = "";
			this.lastUpdated = "";
			this.publisherType = "";
			this.publisher = "";
			
			this.urls = "";
			
			this.randomInfo = randomInfo;	
		}	
	

	
}
