import unittest
import os
import json
from datetime import datetime

from covid_impact import analyze_covid_impact, write_summary_to_json
from covid_data_analysis import find_top_k_deaths, find_top_k_cases, distribute_deaths_to_age_groups, \
    find_least_exposed_age_group, find_most_exposed_age_group, \
    get_total_deaths, get_total_cases, total_cases_and_deaths_per_1m
from read_data import process_daily_data, process_weekly_data


class TestCovidDataProcessing(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.db = {}
        process_daily_data(cls.db)
        process_weekly_data(cls.db)

    def test_specific_date_cases_and_deaths(self):
        austria_data = self.db['AT']
        specific_date_record = next(
            (record for record in austria_data['daily_records'] if record['date'] == '01/04/2021'), None)
        self.assertIsNotNone(specific_date_record, "Specific date record should not be None")
        self.assertEqual(specific_date_record['cases'], 3107, "Cases do not match expected value")
        self.assertEqual(specific_date_record['deaths'], 28, "Deaths do not match expected value")

    def test_weekly_data_age_group(self):
        austria_data = self.db['AT']
        week_10_2021_data = austria_data['weekly_records']['<15yr']
        self.assertTrue(any(record['year_week'] == '2021-10' for record in week_10_2021_data),
                        "Weekly data for age group <15yr in week 10 of 2021 should exist")

    def test_population_data_integrity(self):
        austria_data = self.db['AT']
        self.assertEqual(austria_data['population'], 8901064, "Population does not match expected value")
        self.assertNotEqual(austria_data['population_by_agegroup']['<15yr'], -1,
                            "Population by age group <15yr should not be -1")

    def test_date_range_inclusion_daily_data(self):
        austria_data = self.db['AT']
        early_date_record = next((record for record in austria_data['daily_records'] if
                                  datetime.strptime(record['date'], '%d/%m/%Y') <
                                  datetime(2021, 3, 8)), None)
        self.assertIsNone(early_date_record, "Records before 08/03/2021 should not be included")


class TestIntegrationOfAnalysisAndOutput(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        # Setup with some predefined or dummy data
        cls.db = {}
        # Populate the db with necessary data structures, usually done by other functions
        process_daily_data(cls.db)
        process_weekly_data(cls.db)

    def test_output_of_analysis_written_correctly(self):
        # Get the results from the analysis function
        results = analyze_covid_impact(self.db)

        # Use the results to write to a JSON file
        write_summary_to_json(results)

        # Read back the JSON file to verify its contents
        expected_file_path = 'covid_impact_summary.json'
        self.assertTrue(os.path.exists(expected_file_path), "The JSON file was not created.")

        with open(expected_file_path, 'r') as file:
            data = json.load(file)

        # Construct expected data format based on what analyze_covid_impact should return
        expected_data = {
            "Highest_Deaths_per_1M": {
                "Country": results[0],
                "Deaths_per_1M": results[1]
            },
            "Highest_Cases_per_1M": {
                "Country": results[2],
                "Cases_per_1M": results[3]
            },
            "Most_Exposed_Age_Group": results[4],
            "Least_Exposed_Age_Group": results[5]
        }

        # Check if the data written matches expected data
        self.assertEqual(data, expected_data, "The content of the JSON file does not match expected output.")

        # Cleanup the file after test
        os.remove(expected_file_path)

    @classmethod
    def tearDownClass(cls):
        # Clean up any persistent data or files if needed at the end of tests
        pass


class TestCountryData(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.test_country = {
            "name": "Testland",
            "population": 1000000,  # 1 million people
            "daily_records": [
                {"date": "2020-01-01", "cases": 100, "deaths": 1},
                {"date": "2020-01-02", "cases": 200, "deaths": 2},
                {"date": "2020-01-03", "cases": 150, "deaths": 3}
            ],
            "weekly_records": {
                "<15yr": [{"new_cases": 50}, {"new_cases": 75}],
                "15-24yr": [{"new_cases": 100}, {"new_cases": 150}],
                "25-49yr": [{"new_cases": 150}, {"new_cases": 100}],
                "50-64yr": [{"new_cases": 50}, {"new_cases": 25}],
                "65-79yr": [{"new_cases": 25}, {"new_cases": 15}],
                "80+yr": [{"new_cases": 10}, {"new_cases": 5}],
            },
            "population_by_agegroup": {
                "<15yr": 200000,
                "15-24yr": 150000,
                "25-49yr": 350000,
                "50-64yr": 200000,
                "65-79yr": 75000,
                "80+yr": 25000
            }
        }

    def test_get_total_cases(self):
        self.assertEqual(get_total_cases(self.test_country), 450, "Total cases should be 450.")

    def test_get_total_deaths(self):
        self.assertEqual(get_total_deaths(self.test_country), 6, "Total deaths should be 6.")

    def test_total_cases_and_deaths_per_1m(self):
        cases_per_1m, deaths_per_1m = total_cases_and_deaths_per_1m(self.test_country)
        self.assertEqual(cases_per_1m, 450, "Cases per 1 million should be 450.")
        self.assertEqual(deaths_per_1m, 6, "Deaths per 1 million should be 6.")

    def test_find_most_exposed_age_group(self):
        self.assertEqual(find_most_exposed_age_group(self.test_country), "15-24yr",
                         "Most exposed age group should be 15-24yr.")

    def test_find_least_exposed_age_group(self):
        self.assertEqual(find_least_exposed_age_group(self.test_country), "50-64yr",
                         "Least exposed age group should be 50-64yr.")

    def test_distribute_deaths_to_age_groups(self):
        total_deaths, death_distribution = distribute_deaths_to_age_groups(self.test_country)
        self.assertEqual(total_deaths, 6, "Total deaths should be 6.")
        # Additional assertions could be added to verify the correctness of death_distribution

    def test_find_top_k_cases(self):
        self.assertIn("2020-01-02", find_top_k_cases(self.test_country, 1),
                      "Top 1 case date should include 2020-01-02.")

    def test_find_top_k_deaths(self):
        self.assertIn("2020-01-03", find_top_k_deaths(self.test_country, 1),
                      "Top 1 death date should include 2020-01-03.")


class TestProcessBigDataFixedDataset(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.db = {}  # Assuming this will be filled with the fixed dataset
        process_daily_data(cls.db)
        process_weekly_data(cls.db)

    def test_with_fixed_dataset(self):
        # Call process_big_data with the fixed dataset
        (top_death_country, top_death_cnt, top_case_country,
         top_case_cnt, most_age_group, least_age_group) = analyze_covid_impact(self.db)

        # Replace 'Expected...' with actual expected values based on your dataset analysis
        self.assertEqual(top_death_country, 'Hungary')
        self.assertAlmostEqual(top_death_cnt, 1221, places=2)
        self.assertEqual(top_case_country, 'Estonia')
        self.assertAlmostEqual(top_case_cnt, 35878, places=2)
        self.assertEqual(most_age_group, '15-24yr')
        self.assertEqual(least_age_group, '80+yr')


if __name__ == '__main__':
    unittest.main()
