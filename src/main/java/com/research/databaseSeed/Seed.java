package com.research.databaseSeed;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.IO.AdSeekerOntology;
import com.research.databaseAccess.AdvertisementDAO;
import com.research.databaseAccess.UserDAO;
import com.research.databaseObjects.User;
import com.research.ontology.OntologyRelationships;

public class Seed {
	
	private JSONArray bulkAdArray = new JSONArray();

	public void seedProducts()
	{
		seed_car();
		seed_phones();
		seed_processors();
		seed_vga();
		seed_macs();
		seed_tesla();
		
		User user = new UserDAO().GetUser("poornaj");
		
		new AdvertisementDAO().PushBulkAdvertisementToTheDatabase(bulkAdArray,user);
	}
	
	private void seed_tesla()
	{
		pushToArray("Tesla Model S 2016", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				35000000.00, 
				"http://www.technobuffalo.com/wp-content/uploads/2011/12/Tesla-Model-S-Signature-Red-Feature.jpg", 
				"model s");
		
		pushToArray("Tesla Model S 2015", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				30000000.00, 
				"http://www.technobuffalo.com/wp-content/uploads/2011/12/Tesla-Model-S-Signature-Red-Feature.jpg", 
				"model s");
		
		pushToArray("Tesla Model S 2014", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				25000000.00, 
				"http://www.technobuffalo.com/wp-content/uploads/2011/12/Tesla-Model-S-Signature-Red-Feature.jpg", 
				"model s");
		
		pushToArray("Tesla Model S 2013", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				20000000.00, 
				"http://www.technobuffalo.com/wp-content/uploads/2011/12/Tesla-Model-S-Signature-Red-Feature.jpg", 
				"model s");
		
		pushToArray("Tesla Model X 2016 Founder Edition", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				40000000.00, 
				"http://brandchannel.com/wp-content/uploads/2015/09/Tesla-model-x-thumb-400.jpg", 
				"model x");
		
		pushToArray("Tesla Model X 2015", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				30000000.00, 
				"http://brandchannel.com/wp-content/uploads/2015/09/Tesla-model-x-thumb-400.jpg", 
				"model x");
		
		pushToArray("Tesla Model 3 2016", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				7000000.00, 
				"http://specials-images.forbesimg.com/imageserve/56fe4a43e4b009bee2228490/400x400.jpg?fit=scale&background=000000", 
				"model x");
		
		pushToArray("Tesla Model 3 2015", 
				"Tesla first gained widespread attention following their production of the Tesla Roadster, the first fully electric sports car.", 
				600000.00, 
				"http://specials-images.forbesimg.com/imageserve/56fe4a43e4b009bee2228490/400x400.jpg?fit=scale&background=000000", 
				"model x");
		
	}
	
	private void seed_macs()
	{
		pushToArray("iMac Late 2015", 
				"The iMac is a range of all-in-one Macintosh desktop computers designed and built by Apple Inc. It has been the primary part of Apple's consumer desktop offerings since its debut in August 1998, and has evolved through six distinct forms", 
				395000.00, 
				"https://www.jbhifi.com.au/FileLibrary/ProductResources/Images/155285-L-LO.jpg", 
				"imac");
		
		pushToArray("iMac Late 2014", 
				"The iMac is a range of all-in-one Macintosh desktop computers designed and built by Apple Inc. It has been the primary part of Apple's consumer desktop offerings since its debut in August 1998, and has evolved through six distinct forms", 
				345000.00, 
				"https://www.jbhifi.com.au/FileLibrary/ProductResources/Images/155285-L-LO.jpg", 
				"imac");
		
		pushToArray("iMac Late 2013", 
				"The iMac is a range of all-in-one Macintosh desktop computers designed and built by Apple Inc. It has been the primary part of Apple's consumer desktop offerings since its debut in August 1998, and has evolved through six distinct forms", 
				385000.00, 
				"https://www.jbhifi.com.au/FileLibrary/ProductResources/Images/155285-L-LO.jpg", 
				"imac");
		
		pushToArray("iMac Late 2012", 
				"The iMac is a range of all-in-one Macintosh desktop computers designed and built by Apple Inc. It has been the primary part of Apple's consumer desktop offerings since its debut in August 1998, and has evolved through six distinct forms", 
				326000.00, 
				"https://www.jbhifi.com.au/FileLibrary/ProductResources/Images/155285-L-LO.jpg", 
				"imac");
		
		pushToArray("Macbook Pro Late 2015", 
				"The first generation MacBook Pro appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				415000.00, 
				"http://wyverngaming.co.uk/wp-content/uploads/2015/10/MACBPMF839KSA.jpg", 
				"macbook pro");
		
		pushToArray("Macbook Pro Late 2014", 
				"The first generation MacBook Pro appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				342000.00, 
				"http://wyverngaming.co.uk/wp-content/uploads/2015/10/MACBPMF839KSA.jpg", 
				"macbook pro");
		
		pushToArray("Macbook Pro Late 2013", 
				"The first generation MacBook Pro appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				321000.00, 
				"http://wyverngaming.co.uk/wp-content/uploads/2015/10/MACBPMF839KSA.jpg", 
				"macbook pro");
		
		pushToArray("Macbook Pro Late 2012", 
				"The first generation MacBook Pro appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				270000.00, 
				"http://wyverngaming.co.uk/wp-content/uploads/2015/10/MACBPMF839KSA.jpg", 
				"macbook pro");
		
		pushToArray("Macbook Air Late 2012", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				195000.00, 
				"http://maccentre.com/image/cache/catalog/products/MacBook%20Lcd%20Assembly%20Unit/Apple%20MacBook%20Air%20A1369%2013inch-400x400.jpg", 
				"macbook air");
		
		pushToArray("Macbook Air Late 2013", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				145000.00, 
				"http://maccentre.com/image/cache/catalog/products/MacBook%20Lcd%20Assembly%20Unit/Apple%20MacBook%20Air%20A1369%2013inch-400x400.jpg", 
				"macbook air");
		
		pushToArray("Macbook Air Late 2015", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				165000.00, 
				"http://maccentre.com/image/cache/catalog/products/MacBook%20Lcd%20Assembly%20Unit/Apple%20MacBook%20Air%20A1369%2013inch-400x400.jpg", 
				"macbook air");
		
		pushToArray("Mac Pro 2013", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				815000.00, 
				"https://www.prixzone.com/wp-content/uploads/2016/02/mac-pro-2013-400x400.png", 
				"mac pro");
		
		pushToArray("Mac Pro 2014", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				1065000.00, 
				"https://www.prixzone.com/wp-content/uploads/2016/02/mac-pro-2013-400x400.png", 
				"mac pro");
		
		pushToArray("Mac Pro 2015", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				865000.00, 
				"https://www.prixzone.com/wp-content/uploads/2016/02/mac-pro-2013-400x400.png", 
				"mac pro");
		
		pushToArray("Macbook 2015", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				135000.00, 
				"http://blueskytechnology.biz/image/cache/catalog/MacBook-400x400.jpg", 
				"macbook");
		
		pushToArray("Macbook 2016", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				155000.00, 
				"http://blueskytechnology.biz/image/cache/catalog/MacBook-400x400.jpg", 
				"macbook");
		
		pushToArray("Macbook 2017", 
				"The first generation MacBook Air appeared externally similar to the PowerBook G4, but used the Intel Core processors instead of PowerPC G4 chips. The 15-inch model was introduced first, in January 2006; the 17-inch model followed in April. Both received several updates and Core 2 Duo processors later that year.", 
				165000.00, 
				"http://blueskytechnology.biz/image/cache/catalog/MacBook-400x400.jpg", 
				"macbook");
	}

	
	private void seed_vga()
	{
		pushToArray("Nvidia GTX 750 - 2016", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				35000.00, 
				"https://assetscdn.paytm.com/images/catalog/product/C/CO/COMGIGABYTE-NVIAC-I125591F6011411/2.jpg", 
				"gtx 750");
		
		pushToArray("Nvidia GTX 750 - 2015", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				36000.00, 
				"https://assetscdn.paytm.com/images/catalog/product/C/CO/COMGIGABYTE-NVIAC-I125591F6011411/2.jpg", 
				"gtx 750");
		
		pushToArray("Nvidia GTX 750 - 2014", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				37000.00, 
				"https://assetscdn.paytm.com/images/catalog/product/C/CO/COMGIGABYTE-NVIAC-I125591F6011411/2.jpg", 
				"gtx 750");
		
		pushToArray("Nvidia GTX 1080 - 2013", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				140000.00, 
				"http://www.pckuwait.com/wp-content/uploads/2016/06/msi-1080-400x400.jpg", 
				"gtx 1080");
		
		pushToArray("Nvidia GTX 1080 - 2012", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				141000.00, 
				"http://www.pckuwait.com/wp-content/uploads/2016/06/msi-1080-400x400.jpg", 
				"gtx 1080");
		
		pushToArray("Nvidia GTX 1080 - 2011", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				142000.00, 
				"http://www.pckuwait.com/wp-content/uploads/2016/06/msi-1080-400x400.jpg", 
				"gtx 1080");
		
		pushToArray("Nvidia GTX 1070 - 2021", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				85000.00, 
				"http://www.pckuwait.com/wp-content/uploads/2016/06/msi-1080-400x400.jpg", 
				"gtx 1070");
		
		pushToArray("Nvidia GTX 1070 - 2013", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				86000.00, 
				"http://www.pckuwait.com/wp-content/uploads/2016/06/msi-1080-400x400.jpg", 
				"gtx 1070");
		
		pushToArray("Nvidia GTX 1070 - 2014", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				87000.00, 
				"http://www.pckuwait.com/wp-content/uploads/2016/06/msi-1080-400x400.jpg", 
				"gtx 1070");
		
		pushToArray("Nvidia GTX Titan X - 2013", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				155000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx titan x");
		
		pushToArray("Nvidia GTX Titan X - 2014", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				156000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx titan x");
		
		pushToArray("Nvidia GTX Titan X - 2015", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				160000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx titan x");
		
		pushToArray("Nvidia GTX 980 - 2016", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				75000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx 980");
		
		pushToArray("Nvidia GTX 980 - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				77000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx 980");
		
		pushToArray("Nvidia GTX 980 - 2018", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				78000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx 980");
		
		pushToArray("Nvidia GTX 980 Ti - 2019", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				71000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx 980 ti");
		
		pushToArray("Nvidia GTX 980 Ti - 2013", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				72000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx 980 ti");
		
		pushToArray("Nvidia GTX 980 Ti - 2014", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				73000.00, 
				"http://thumbs4.picclick.com/d/l400/pict/291841605167_/EVGA-NVidia-Geforce-GTX-1080-GAMING-ACX-30.jpg", 
				"gtx 980 ti");
		
		pushToArray("AMD R9 480 - 2015", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the worldr", 
				74000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 480");
		
		pushToArray("AMD R9 480 - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				85000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 480");
		
		pushToArray("AMD R9 480 - 2015", 
				"Best 8GB Moster 3 Best 8GB Moster 3 Best 8GB Moster 3 Best 8GB Moster 3 Best 8GB Moster 3", 
				95000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 480");
		
		pushToArray("Nvidia GTX 780 Ti - 2013", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				65000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"gtx 780 ti");
		
		pushToArray("Nvidia GTX 780 Ti - 2012", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				61000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"gtx 780 ti");
		
		pushToArray("Nvidia GTX 780 Ti - 2014", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				62000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"gtx 780 ti");
		
		pushToArray("AMD R9 390X - 2016", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				83000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 390x");
		
		pushToArray("AMD R9 390X - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				85000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 390x");
		
		pushToArray("AMD R9 390X - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				87000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 390x");
		
		pushToArray("AMD Gigabyte R9 380X - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				45000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 380x");
		
		pushToArray("AMD Gigabyte R9 380X - 2071", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				42000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 380x");
		
		pushToArray("AMD Gigabyte R9 fury - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				48000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 fury");
		
		pushToArray("AMD Gigabyte R9 nano - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				45000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 nano");
		
		pushToArray("AMD Gigabyte R9 fury - 2018", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				42000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 fury");
		
		pushToArray("AMD Gigabyte R9 370 - 2017", 
				"addition to GPU manufacturing, Nvidia provides parallel processing capabilities to researchers and scientists that allow them to efficiently run high-performance applications. They are deployed in supercomputing sites around the world", 
				48000.00, 
				"http://www.ubbcentral.com/store/item/img-large/sapphire-amd-ati-radeon-r9-fury-x-graphics-card-4gb-ddr5-hbm-pci-e-hdmi_391308988558.jpg", 
				"r9 370");
		
		

	}
	
	private void seed_car()
	{
		pushToArray("Toyota Corolla", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				3500000.00, 
				"http://www.efleet.in/image/cache/catalog/car/corolla-400x400.jpg", 
				"corolla");
		
		pushToArray("Toyota Corolla 2014", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				3600000.00, 
				"http://www.efleet.in/image/cache/catalog/car/corolla-400x400.jpg", 
				"corolla");
		
		pushToArray("Toyota Corolla 2015", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				3700000.00, 
				"http://www.efleet.in/image/cache/catalog/car/corolla-400x400.jpg", 
				"corolla");
		
		pushToArray("Toyota Corolla 2016", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				3800000.00, 
				"http://www.efleet.in/image/cache/catalog/car/corolla-400x400.jpg", 
				"corolla");
		
		pushToArray("Toyota Premio", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				9500000.00, 
				"http://cdn1.anunico-st.com/foto/2015/12/_2011_toyota_premio_a_big_deal_-21839a25edba68e5dd66780d7ed7f54b.jpg", 
				"premio");
		
		pushToArray("Toyota Premio 2017", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				9700000.00, 
				"http://cdn1.anunico-st.com/foto/2015/12/_2011_toyota_premio_a_big_deal_-21839a25edba68e5dd66780d7ed7f54b.jpg", 
				"premio");
		
		pushToArray("Toyota Premio 2016", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				9800000.00, 
				"http://cdn1.anunico-st.com/foto/2015/12/_2011_toyota_premio_a_big_deal_-21839a25edba68e5dd66780d7ed7f54b.jpg", 
				"premio");
		
		pushToArray("Toyota Premio 2015", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				9900000.00, 
				"http://cdn1.anunico-st.com/foto/2015/12/_2011_toyota_premio_a_big_deal_-21839a25edba68e5dd66780d7ed7f54b.jpg", 
				"premio");
		
		pushToArray("Toyota Allion", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				7500000.00, 
				"http://auto-lanka.com/img.web/userimage/160612215916_image1.jpg", 
				"allion");
		
		pushToArray("Toyota Allion 2010", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				7600000.00, 
				"http://auto-lanka.com/img.web/userimage/160612215916_image1.jpg", 
				"allion");
		
		pushToArray("Toyota Allion 2012", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				7700000.00, 
				"http://auto-lanka.com/img.web/userimage/160612215916_image1.jpg", 
				"allion");
		
		pushToArray("Toyota Prado", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				27500000.00, 
				"http://pkprices.com/wp-content/uploads/2014/10/New-Toyota-Prado-2014-Price-In-Pakistan-Silver.jpg", 
				"prado");
		
		pushToArray("Toyota Prado 2017", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				28500000.00, 
				"http://pkprices.com/wp-content/uploads/2014/10/New-Toyota-Prado-2014-Price-In-Pakistan-Silver.jpg", 
				"prado");
		
		pushToArray("Toyota Prado 2016", 
				"Toyota is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				29500000.00, 
				"http://pkprices.com/wp-content/uploads/2014/10/New-Toyota-Prado-2014-Price-In-Pakistan-Silver.jpg", 
				"prado");
		
		pushToArray("Nissan Sunny FB15 2011", 
				"Nissan is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				2500000.00, 
				"http://imganuncios.mitula.net/nissan_sunny_1_6_auto_for_rent_96635406177036934.jpg", 
				"fb15");
		
		pushToArray("Nissan Sunny FB15 2010", 
				"Nissan is the world's market leader in sales of hybrid electric vehicles, and one of the largest companies to encourage the mass-market adoption of hybrid vehicles across the globe", 
				2800000.00, 
				"http://imganuncios.mitula.net/nissan_sunny_1_6_auto_for_rent_96635406177036934.jpg", 
				"fb15");
		
		pushToArray("Nissan Sunny FB15 2006", 
				"Nissan Motor sells its cars under the Nissan, Infiniti, and Datsun brands with in-house performance tuning products labelled Nismo.", 
				2300000.00, 
				"http://imganuncios.mitula.net/nissan_sunny_1_6_auto_for_rent_96635406177036934.jpg", 
				"fb15");
		
		pushToArray("Nissan Sunny FB14 2004", 
				"Nissan Motor sells its cars under the Nissan, Infiniti, and Datsun brands with in-house performance tuning products labelled Nismo.", 
				2200000.00, 
				"http://imganuncios.mitula.net/nissan_sunny_1_6_auto_for_rent_96635406177036934.jpg", 
				"fb14");
		
		pushToArray("Toyota Prius", 
				"Nissan Motor sells its cars under the Nissan, Infiniti, and Datsun brands with in-house performance tuning products labelled Nismo.", 
				5500000.00, 
				"https://247wallst.files.wordpress.com/2015/11/prius_exterior.jpg?w=400", 
				"prius");
		
		pushToArray("Toyota Prius 2017", 
				"Nissan Motor sells its cars under the Nissan, Infiniti, and Datsun brands with in-house performance tuning products labelled Nismo.", 
				5800000.00, 
				"https://247wallst.files.wordpress.com/2015/11/prius_exterior.jpg?w=400", 
				"prius");
		
		pushToArray("Toyota Prius 2016", 
				"Nissan Motor sells its cars under the Nissan, Infiniti, and Datsun brands with in-house performance tuning products labelled Nismo.", 
				5300000.00, 
				"https://247wallst.files.wordpress.com/2015/11/prius_exterior.jpg?w=400", 
				"prius");
		
		pushToArray("Toyota Prius 2015", 
				"Nissan Motor sells its cars under the Nissan, Infiniti, and Datsun brands with in-house performance tuning products labelled Nismo.", 
				6500000.00, 
				"https://247wallst.files.wordpress.com/2015/11/prius_exterior.jpg?w=400", 
				"prius");
		
		pushToArray("Mercedes Benz E300", 
				"Benz gained widespread attention following their production of the Benz Roadster, the first fully electric sports car.", 
				35000000.00, 
				"http://cdn.pursuitist.com/wp-content/uploads/2010/08/Mercedes-Benz-new-CLS-Class.2-400x400.jpg", 
				"e300");
		
		pushToArray("Mercedes Benz E300 AMG", 
				"Benz first gained widespread attention following their production of the Benz Roadster, the first fully electric sports car.", 
				45000000.00, 
				"http://cdn.pursuitist.com/wp-content/uploads/2011/03/2012_C63_AMG_Coupe_2-400x400.jpg", 
				"e300 amg");
		
		pushToArray("Mercedes Benz E300", 
				"Benz first gained widespread attention following their production of the Benz Roadster, the first fully electric sports car.", 
				38000000.00, 
				"http://cdn.pursuitist.com/wp-content/uploads/2010/08/Mercedes-Benz-new-CLS-Class.2-400x400.jpg", 
				"e300");
		
		pushToArray("Mercedes Benz E300 AMG", 
				"Benz first gained widespread attention following their production of the Benz Roadster, the first fully electric sports car.", 
				48000000.00, 
				"http://cdn.pursuitist.com/wp-content/uploads/2011/03/2012_C63_AMG_Coupe_2-400x400.jpg", 
				"e300 amg");
		
	}
	
	private void seed_phones()
	{
		pushToArray("Apple iPhone 6S Plus", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				120000.00, 
				"https://secure.telkom.co.za/today/media/photologue/photos/cache/iphone-6s-plus-1_prbalp2_6igbtyz_2iPemRC_picture_srcset_x400.png", 
				"iphone 6s plus");
		
		pushToArray("Apple iPhone 6S Plus Black", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				130000.00, 
				"https://secure.telkom.co.za/today/media/photologue/photos/cache/iphone-6s-plus-1_prbalp2_6igbtyz_2iPemRC_picture_srcset_x400.png", 
				"iphone 6s plus");
		
		pushToArray("Apple iPhone 6S Plus Gold", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				140000.00, 
				"http://www.click.ie/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/2/2212.jpg", 
				"iphone 6s plus");
		
		pushToArray("Apple iPhone 6S", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				100000.00, 
				"http://www.click.ie/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/2/2212.jpg", 
				"iphone 6s");
		
		pushToArray("Apple iPhone 6S Gold", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				110000.00, 
				"https://secure.telkom.co.za/today/media/photologue/photos/cache/iphone-6s-plus-1_prbalp2_6igbtyz_2iPemRC_picture_srcset_x400.png", 
				"iphone 6s");
		
		pushToArray("Apple iPhone 6S Silver", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				120000.00, 
				"http://www.click.ie/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/2/2212.jpg", 
				"iphone 6s");
		
		pushToArray("Apple iPhone 6 Plus", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				123000.00, 
				"https://secure.telkom.co.za/today/media/photologue/photos/cache/iphone-6s-plus-1_prbalp2_6igbtyz_2iPemRC_picture_srcset_x400.png", 
				"iphone 6 plus");
		
		pushToArray("Apple iPhone 6 Plus Gold", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				125000.00, 
				"http://www.click.ie/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/2/2212.jpg", 
				"iphone 6 plus");
		
		pushToArray("Apple iPhone 6 Plus Silver", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				126000.00, 
				"https://secure.telkom.co.za/today/media/photologue/photos/cache/iphone-6s-plus-1_prbalp2_6igbtyz_2iPemRC_picture_srcset_x400.png", 
				"iphone 6 plus");
		
		pushToArray("Apple iPhone 5S", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				70000.00, 
				"http://www.click.ie/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/2/2212.jpg", 
				"iphone 5s");
		
		pushToArray("Apple iPhone 5S Gold", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				72000.00, 
				"https://secure.telkom.co.za/today/media/photologue/photos/cache/iphone-6s-plus-1_prbalp2_6igbtyz_2iPemRC_picture_srcset_x400.png", 
				"iphone 5s");
		
		pushToArray("Apple iPhone 5S Silver", 
				"Apple has released nine generations of iPhone models, each accompanied by one of the nine major releases of the iOS operating system.", 
				73000.00, 
				"http://www.click.ie/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/2/2212.jpg", 
				"iphone 5s");
		
		pushToArray("Samsung Galaxy S7 64GB", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				100000.00, 
				"https://2.bp.blogspot.com/-0AxafN9Nal8/VsrkF31U8PI/AAAAAAAADPo/Rw133uLsQioXJjmZeMBBcvaX-P5S76yXACKgB/s1600/S7%2Bedge%2B600x600.jpg", 
				"galaxy s7");
		
		pushToArray("Samsung Galaxy S7 128GB", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				110000.00, 
				"https://2.bp.blogspot.com/-0AxafN9Nal8/VsrkF31U8PI/AAAAAAAADPo/Rw133uLsQioXJjmZeMBBcvaX-P5S76yXACKgB/s1600/S7%2Bedge%2B600x600.jpg", 
				"galaxy s7");
		
		pushToArray("Samsung Galaxy S7 Gold", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				120000.00, 
				"https://2.bp.blogspot.com/-0AxafN9Nal8/VsrkF31U8PI/AAAAAAAADPo/Rw133uLsQioXJjmZeMBBcvaX-P5S76yXACKgB/s1600/S7%2Bedge%2B600x600.jpg", 
				"galaxy s7");
		
		pushToArray("Samsung Galaxy S7 Edge Silver", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				130000.00, 
				"https://2.bp.blogspot.com/-0AxafN9Nal8/VsrkF31U8PI/AAAAAAAADPo/Rw133uLsQioXJjmZeMBBcvaX-P5S76yXACKgB/s1600/S7%2Bedge%2B600x600.jpg", 
				"galaxy s7 edge");
		
		pushToArray("Samsung Galaxy S7 Edge Gold", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				112000.00, 
				"https://2.bp.blogspot.com/-0AxafN9Nal8/VsrkF31U8PI/AAAAAAAADPo/Rw133uLsQioXJjmZeMBBcvaX-P5S76yXACKgB/s1600/S7%2Bedge%2B600x600.jpg", 
				"galaxy s7 edge");
		
		pushToArray("Samsung Galaxy S7 Edge Silver", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				136000.00, 
				"http://www.oled-info.com/files/styles/large/public/Samsung-Galaxy-Note-7.jpg?itok=N-tKOLV5", 
				"galaxy s7 edge");
		
		pushToArray("Samsung Galaxy S6", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				700000.00, 
				"https://images.konga.com/v2/media/catalog/product/G/a/Galaxy-S7---32GB---Duos---Gold-4050714.jpg", 
				"galaxy s6");
		
		pushToArray("Samsung Galaxy S6 Black", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				720000.00, 
				"http://www.oled-info.com/files/styles/large/public/Samsung-Galaxy-Note-7.jpg?itok=N-tKOLV5", 
				"galaxy s6");
		
		pushToArray("Samsung Galaxy S6 32GB", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				750000.00, 
				"https://images.konga.com/v2/media/catalog/product/G/a/Galaxy-S7---32GB---Duos---Gold-4050714.jpg", 
				"galaxy s6");
		
		pushToArray("Samsung Galaxy Note 7 Black", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				900000.00, 
				"http://www.oled-info.com/files/styles/large/public/Samsung-Galaxy-Note-7.jpg?itok=N-tKOLV5", 
				"note 7");
		
		pushToArray("Samsung Galaxy Note 5 Silver", 
				"Samsung Galaxy devices have traditionally used the Android operating system produced by the Open Handset Alliance led by Google", 
				700000.00, 
				"https://images.konga.com/v2/media/catalog/product/G/a/Galaxy-S7---32GB---Duos---Gold-4050714.jpg", 
				"note 5");
		
		pushToArray("One plus 3", 
				"OnePlus was founded on 16 December 2013 by former Oppo vice president Pete Lau", 
				600000.00, 
				"http://en1.data.coolicool.com/images/MHAOP2G/MHAOP2G/A1_1.images.400x400.jpg", 
				"one plus 3");
		
		pushToArray("One plus 3 Silver", 
				"OnePlus was founded on 16 December 2013 by former Oppo vice president Pete Lau", 
				700000.00, 
				"http://en1.data.coolicool.com/images/MHAOP2G/MHAOP2G/A1_1.images.400x400.jpg", 
				"one plus 3");
		
		pushToArray("One plus 2", 
				"OnePlus was founded on 16 December 2013 by former Oppo vice president Pete Lau", 
				500000.00, 
				"http://en1.data.coolicool.com/images/MHAOP2G/MHAOP2G/A1_1.images.400x400.jpg", 
				"one plus 3");
		
		pushToArray("One plus 2 gold", 
				"OnePlus was founded on 16 December 2013 by former Oppo vice president Pete Lau", 
				550000.00, 
				"http://en1.data.coolicool.com/images/MHAOP2G/MHAOP2G/A1_1.images.400x400.jpg", 
				"one plus 3");
		
		pushToArray("HTC 10", 
				"manufacturers and mobile network operators dedicated to the development of the Android mobile operating system.", 
				550000.00, 
				"http://www.gadgetdetail.com/wp-content/uploads/2016/04/htc-10-400x400.png", 
				"htc 10");
		
		pushToArray("HTC 10 64GB", 
				"manufacturers and mobile network operators dedicated to the development of the Android mobile operating system.", 
				750000.00, 
				"http://www.gadgetdetail.com/wp-content/uploads/2016/04/htc-10-400x400.png", 
				"htc 10");
		
		pushToArray("HTC 10 128GB Black", 
				"manufacturers and mobile network operators dedicated to the development of the Android mobile operating system.", 
				850000.00, 
				"http://www.gadgetdetail.com/wp-content/uploads/2016/04/htc-10-400x400.png", 
				"htc 10");
		
		pushToArray("Nexus 6p", 
				"manufacturers and mobile network operators dedicated to the development of the Android mobile operating system.", 
				750000.00, 
				"https://assets.goodhousekeeping.co.uk/main/embedded/27080/nexus-6p-graphite-good-housekeeping__medium.jpg?20150930145830", 
				"nexus 6p");
		
		pushToArray("Nexus 6p gold", 
				"manufacturers and mobile network operators dedicated to the development of the Android mobile operating system.", 
				850000.00, 
				"https://assets.goodhousekeeping.co.uk/main/embedded/27080/nexus-6p-graphite-good-housekeeping__medium.jpg?20150930145830", 
				"nexus 6p");
		
		pushToArray("Nexus 5x", 
				"manufacturers and mobile network operators dedicated to the development of the Android mobile operating system.", 
				550000.00, 
				"http://www.miodeals.com/media/catalog/product/cache/1/image/400x400/9df78eab33525d08d6e5fb8d27136e95/l/g/lg-nexus-5x-.jpg", 
				"nexus 5x");
		
		pushToArray("Nexus 5x 64GB", 
				"manufacturers and mobile network operators dedicated to the development of the Android mobile operating system.", 
				580000.00, 
				"http://www.miodeals.com/media/catalog/product/cache/1/image/400x400/9df78eab33525d08d6e5fb8d27136e95/l/g/lg-nexus-5x-.jpg", 
				"nexus 5x");
		
	}
	
	private void seed_processors()
	{
		pushToArray("Intel Core i7 6950x", 
				"Intel 10 Core with HT", 
				280000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i7");
		
		pushToArray("Intel Core i7 5960x", 
				"Intel 8 Core with HT", 
				180000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i7");
		
		pushToArray("Intel Core i7 4960x", 
				"Intel 6 Core with HT", 
				140000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/slideshows/2014/intel_core_i7/slide-1.jpg", 
				"i7");
		
		pushToArray("Intel Core i7 3770k", 
				"Intel 4 Core with HT", 
				60000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/slideshows/2014/intel_core_i7/slide-1.jpg", 
				"i7");
		
		pushToArray("Intel Core i7 4790k", 
				"Intel 4 Core with HT", 
				70000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/slideshows/2014/intel_core_i7/slide-1.jpg", 
				"i7");
		
		pushToArray("Intel Core i7 6770k", 
				"Intel 4 Core with HT", 
				80000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i7");
		
		pushToArray("Intel Core i5", 
				"Intel Quad Core for gaming", 
				350000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i5");
		
		pushToArray("Intel Core i5 3.5GHz", 
				"Intel Quad Core for gaming", 
				370000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i5");
		
		pushToArray("Intel Core i5 3.8GHz", 
				"Intel Quad Core for gaming", 
				390000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i5");
		
		pushToArray("Intel Core i3 4GHz", 
				"Intel Dual Core for multimedia", 
				180000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i3");
		
		pushToArray("Intel Core i3 3.1GHz", 
				"Intel Dual Core for multimedia", 
				190000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i3");
		
		pushToArray("Intel Core i3", 
				"Intel Dual Core for multimedia", 
				150000.00, 
				"http://www.crn.com/ckfinder/userfiles/images/crn/misc/2015/intel-chip400(1).jpg", 
				"i3");
		
		pushToArray("AMD 8 Core Processor", 
				"Budget 8 Core gaming", 
				280000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 6 Core Processor 2000k", 
				"Budget 6 Core gaming", 
				250000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 4 Core Processor 3000k", 
				"Budget 4 Core gaming", 
				220000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 8 Core Processor 8000k", 
				"Budget 8 Core gaming", 
				320000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 12 Core Processor 30000k", 
				"Budget 12 Core gaming", 
				350000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 8 Core Processor 8000k", 
				"Budget 8 Core gaming", 
				240000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 12 Core Processor 12000k", 
				"Budget 12 Core gaming", 
				550000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 12 Core Processor 30000k", 
				"Budget 12 Core gaming", 
				350000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 18 Core Processor 98000k", 
				"18 Core gaming", 
				104000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");
		
		pushToArray("AMD 12 Core Processor 17000k", 
				"Budget 12 Core gaming", 
				590000.00, 
				"http://www.argoncomputing.co.uk/wp-content/uploads/2015/12/AMD-FX8320X8-400x400.jpg", 
				"amd cpu");

	}
	
	private void pushToArray(String productTitle, String productDescription, Double productPrice, String imageUrl, String relationshipName)
	{
		
		JSONObject advertisement = new JSONObject();
		advertisement.put("title", productTitle);
		advertisement.put("description", productDescription);
		advertisement.put("imageurl", imageUrl);
		advertisement.put("price", productPrice);
		
		OntologyRelationships relationships = new OntologyRelationships();
		ArrayList<String> ral = relationships.listOntologyRelationshipInArrayList(relationshipName, AdSeekerOntology.getModel(), AdSeekerOntology.getOntologyURI());
		JSONArray rja = new JSONArray();
		
		for (String string : ral) {
			rja.add(string);
		}
		
		advertisement.put("relationship", rja);
		
		bulkAdArray.add(advertisement);
		
		System.out.println(bulkAdArray.toJSONString());
		
	}
	
	private void save(String productTitle, String productDescription, Double productPrice, String imageUrl, String relationshipName)
	{
		
		User user = new UserDAO().GetUser("poornapsj");
		
		JSONObject advertisement = new JSONObject();
		advertisement.put("title", productTitle);
		advertisement.put("description", productDescription);
		advertisement.put("imageurl", imageUrl);
		advertisement.put("price", productPrice);
		
		OntologyRelationships relationships = new OntologyRelationships();
		ArrayList<String> ral = relationships.listOntologyRelationshipInArrayList(relationshipName, AdSeekerOntology.getModel(), AdSeekerOntology.getOntologyURI());
		JSONArray rja = new JSONArray();
		
		for (String string : ral) {
			rja.add(string);
		}
		
		advertisement.put("relationship", rja);
		
		new AdvertisementDAO().PushNewAdvertisementToTheDatabase(advertisement,user);
		
	}
	

}
