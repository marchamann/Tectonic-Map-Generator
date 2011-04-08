package ca.hamann.mapgen.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ca.hamann.mapgen.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestPlateGenerator.class);
		suite.addTestSuite(TestCoastInlandDivider.class);
		suite.addTestSuite(TestNonLinearMapStorage.class);
		suite.addTestSuite(TestMapConfigurationSerializer.class);
		suite.addTestSuite(TestPlateDrifter.class);
		suite.addTestSuite(TestNeighbourhoods.class);
		suite.addTestSuite(TestLattitudeDrifter.class);
		suite.addTestSuite(TestDirectionSequence.class);
		suite.addTestSuite(TestTectonicValues.class);
		suite.addTestSuite(TestSinusoidalProjection.class);
		suite.addTestSuite(TestPlateSplitter.class);
		suite.addTestSuite(TestBasicMover.class);
		suite.addTestSuite(TestSeaDrainageDivider.class);
		suite.addTestSuite(TestLakeFiller.class);
		suite.addTestSuite(TestSequenceJoiner.class);
		suite.addTestSuite(TestLandSeaDivider.class);
		suite.addTestSuite(TestRiverMaker.class);
		suite.addTestSuite(TestSubductionRelation.class);
		suite.addTestSuite(TestPlateSerializer.class);
		suite.addTestSuite(TestSinusoidalMap.class);
		suite.addTestSuite(TestDrainageProcessor.class);
		suite.addTestSuite(TestRotater.class);
		suite.addTestSuite(TestSinusoidalLocation.class);
		suite.addTestSuite(TestSequenceFiller.class);
		suite.addTestSuite(TestEroder.class);
		suite.addTestSuite(TestClosureDivider.class);
		suite.addTestSuite(TestSinusoidalMapStorage.class);
		suite.addTestSuite(TestDrainageGraph.class);
		suite.addTestSuite(TestTectonicPlates.class);
		suite.addTestSuite(TestCylindricalProjection.class);
		//$JUnit-END$
		return suite;
	}
}
