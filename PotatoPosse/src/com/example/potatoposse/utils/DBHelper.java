package com.example.potatoposse.utils;

import android.database.sqlite.SQLiteDatabase;

public class DBHelper 
{
	private SQLiteDatabase db;
	
	public DBHelper(SQLiteDatabase db)
	{
		this.db = db;
	}
	
	public void runTests()
	{
		db.execSQL("DROP TABLE IF EXISTS tests");
		
		String CREATE = "CREATE TABLE tests (" + 
			"id int," +
			"name TEXT," +
			"PRIMARY KEY ( id )" +
		");";
		db.execSQL(CREATE);
	}
	
	public void runProblems()
	{
		db.execSQL("DROP TABLE IF EXISTS problems");
		
		String CREATE = "CREATE TABLE problems (" +
		    "id int," + 
		    "name TEXT," +
		    "leaf boolean," +
		    "pest boolean," +
		    "tuber boolean," +
		    "description TEXT," +
		    "control TEXT," +
		    "test1 int," +
		    "test2 int," +
		    "PRIMARY KEY ( id )," +
		    "FOREIGN KEY ( test1 ) REFERENCES tests ( id )," +
		    "FOREIGN KEY ( test2 ) REFERENCES tests ( id )" +
	    ");";
		db.execSQL(CREATE);

		String INSERT = "INSERT INTO problems (id, name, leaf, pest, tuber, description, control, test1, test2) " +
			"VALUES" + 
			"(1,'Potato Virus Y (PVY)', 1,0, 0, 'Occurs worldwide in potato, tobacco and vegetable crops (tomato, peppers, lettuce). Spread by many species of aphid and not effectively controlled by insecticide treatment. PVY causes yellowing and mosaic symptoms in leaves and in mixed infection with PVX symptoms can be severe yellow mosaic and leaf distortion.','If seed crop remove and destroy plant. If ware crop, mark the plants with a cane and harvest tubers for consumption. Tubers from infected plants should not be used for propagation.', 1, 2)," +
			"(2,'Potato Virus X (PVX)', 1,0,0, 'Occurs worldwide in potato. Spread by manual contact. Generally symptoms are mild, with pale green mosaic or patchy blotches on leaves but in mixed infection with PVY symptoms can be severe yellow mosaic and leaf distortion.', 'If seed crop remove and destroy plant. If ware crop, mark the plants with a cane and harvest tubers for consumption. Tubers from infected plants should not be used for propagation. Care should be taken to disinfect tools and hands after touching infected plants or tubers.', 1,2)," +
			"(3,'Potato leafroll virus', 1,0,0, 'Occurs worldwide in potato. Symptoms are rolling of leaflets and sometimes they feel a little crisp (hard) to the touch when gently squeezed in the hand. Transmitted by aphids(principally Myzus persicae) but not mechanically transmitted.', 'Spread can be controlled by application of systemic insecticide. If seed crop, remove and destroy plant. If ware crop, mark the plants with a cane and harvest tubers for consumption. Tubers from infected plants should not be used for propagation.', 1, 3)," +
			"(4,'Pectobacterium atrosepticum',1,0,1,'Generally more likely to cause disease in cool wet climates. Plants are often stunted with leaf yellowing and stems are black and slimy at base. The foliage wilts as the plant ages. Tubers develop soft rot usually from the stolon end but under very wet condition rots from individual lenticels can develop.', 'Dig up affected plants and place foliage and tubers in bags for destruction away from field. Do not plant diseased tubers. Tubers are a source of infection and should be washed in disinfectant before storage. Clean and disinfect tools and equipment to avoid spreading infection.',1,3)," +
			"(5,'Dickeya solani', 1,0,1,'Serious disease particularly in warm or hot climates causes wilting in top leaves, and brown discolouration through the base of the stem, spreading to the whole plant which dies. Affected tubers may rot or carry latent infection and can survive in the soil. D. solani has a wide host range.','Dig up affected plants and place foliage and tubers in bags for destruction away from field. Do not plant diseased tubers. Do not plant diseased tubers. Tubers are a source of infection and should be washed in disinfectant before storage. Clean and disinfect tools and equipment to avoid spreading infection.' , 1, 3)," +                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
			"(6,'Brown rot / Bacterial wilt', 1,0,0, 'Plants wilt, usually one stem first then spreads to whole plant; plant become yellow and stunted and dies. Stems may display brown streaks at the base and bacterial ooze seeps out of cut stems that is visible as whitish strands when the cut end of stem is placed in water.', 'Dig up affected plants and place foliage and tubers in bags for destruction away from field. Do not plant diseased tubers. Avoid growing potatoes in the same field for several years (use crop rotation with 4-6 seasons between crops) to decrease level of bacteria in soil.',1,2)," +
			"(7,'Common scab',0,0,1,'Occurs worldwide and main impact is on tuber quality. Symptoms are not usually seen on foliage or roots. Scab lesions occur on tubers and can cover large areas of the surface; they are usually raised brown lesions but can also form pits. High soil moisture inhibits growth.','Use irrigation during tuber formation to control development and do not grow successive potato crops on same land.', 1, 3)," +
			"(8,'Early blight', 1,0,0,'Occurs worldwide where potatoes are grown. More serious in warmer climates, damaging disease occurs in warm wet conditions early in the season. Necrotic lesions are visible on leaves, usually as small black spots that enlarge into brown/necrotic patches which are circular or form interveinal patches sometimes bordered by yellow or chlorotic tissue. Leaf curling can also occur','Application of fungicide to foliage; removal and destruction of infected crop debris after harvest.',1,3)," +
			"(9, 'Black scurf', 0,0,1,'Occurs worldwide wherever potatoes are grown. Primarily causes tuber symptoms but if affected early in growing season plants display lesions on stems, or plant may not emerge appear weakened and stunted. Tuber symptoms are black patches (like adherent soil particles) on the surface sometimes tubers are misshapen with surface pits.','Treat tubers with fungicide such as morceren before planting. Use crop rotation with 4-6 seasons between potato crops', 1, 3)," +
			"(10, 'Fusarium dry rot/wilt',1,0,1,'Occurs in potato production systems worldwide. Mainly affects roots and tubers; root decay causes foliar wilting and death of plants, some plants stunted and necrotic. Tubers symptoms are severe, large patches of black, brown discoloration, pitting and fungal mycelium visible, tubers are inedible.', 'Avoid damage and treat unaffected tubers with fungicide immediately afterharvest (within 24 h); decrease soil contamination by long rotation (do not grow successive potato crops on the same land).', 1,3)," +
			"(11, 'Late blight', 1,0,1, 'Occurs in potato production systems worldwide. Can also cause serious disease on tomato plants. Moist conditions favour disease development. Symptoms on leaflets begin as pale green spots but quickly develop into irregularly shaped necrotic lesions that spread across the surface. On the underside white mycelium is visible at the edges of the necrotic patches. The disease spreads to entire leaves, stems and rest of the plant which kills the plant. Affected tubers are blackened with internal brown rust patches. Some tubers may only show mild symptoms.', 'If wet weather spray on emergence with protectant fungicide. As soon as early symptoms observed spray immediately with recommended systemic fungicide (may depend on LB PCR genotype test). Dead foliage and tubers are reservoirs of infection and should be removed and destroyed. Tubers can be treated with fungicide pre-planting.', 1, 2)," +
			"(12, 'Root knot nematode (RKN)', 0,0,1, 'RKN have a wide host range and occur throughout the world. Affected plants are weakened and chlorotic sometimes wilted due to root damage. The root systems are colonised by the female worms which create large galls (root knots) that disrupt the vascular system causing impaired root function. The galls can be seen by eye on plant root systems.', 'Various chemical formulations are available but these tend to be non-specific. For some crops, plants resistant against the most important species (Meloidogyne incognita) are available.', 1, 3)," +
			"(13, 'Aphids', 0,1,0, 'Aphids occur on potato worldwide. Many aphids colonise potatoes and several transmit virus diseases. Myzus persicae efficiently transmits PLRV and M. persicae, Aphis fabae, and cereal aphids transmit PVY. When present in large numbers they can cause feeding damage and wilting.', 'Apply insecticide treatments on emergence to control PLRV. To avoid virus infection it is essential to grow seed crops in areas with low aphid prevalence.', 3,3)," +
			"(14, 'Tuber moth',  0,1,1,'The larvae of the moth damage tubers by eating into the tuber flesh forming holes and tunnels. The damage can allow bacteria and other microbes to colonise and further damage tubers. It has worldwide distribution and is an important storage pest in developing countries.', 'Monitor populations during growing season with pheromone traps and apply insecticide treatment if needed; ensure proper ridging of plants to prevent exposure of tubers during growing season; protect tubers after harvest by storing under cover and ensure seed stores are clean and free of debris and old potatoes.', 3,3)," +
			"(15, 'Nutrient deficiency', 1,0,0, 'Potassium: Plants can be stunted with fewer stems and loss of older leaves; leaf margins are necrotic with some upward curling Nitrogen: plants are stunted with yellowing leaves and some curling of margins Phosphorus: Stunted plants, crinkled leaves. Leaves have necrotic spots, chlorosis and leaflet margins are necrotic.', 'Remedy: Apply N, P, K fertiliser at recommended rates', 3,3)," +
			"(16, 'Greening', 0,0,1, 'Tubers growing near the surface or exposed at the surface will develop photosynthetic tissue and turn green. Green tissue is high in toxic alkaloids and should not be eaten. Avoid exposure to light by maintaining ridges to ensure tubers well covered with soil and store in the dark.', null,3, 3" +
		");";
		db.execSQL(INSERT);
	}
}