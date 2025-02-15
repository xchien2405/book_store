// document.addEventListener("DOMContentLoaded", async () => {
//     const provinceSelect = document.getElementById("province");
//     const districtSelect = document.getElementById("district");
//     const wardSelect = document.getElementById("ward");

//     try {
//         const response = await fetch("https://provinces.open-api.vn/api/");
//         const data = await response.json();

//         data.forEach(province => {
//             const option = document.createElement("option");
//             option.value = province.code.toString();
//             option.text = province.name;
//             provinceSelect.appendChild(option);
//         });

//         provinceSelect.addEventListener("change", async () => {
//             const selectedProvinceCode = provinceSelect.value;

//             try {
//                 const districtResponse = await fetch(`https://provinces.open-api.vn/api/d/?province_code=${selectedProvinceCode}`);
//                 const districts = await districtResponse.json();

//                 fillDistricts(districts, districtSelect, provinceSelect, wardSelect);
//             } catch (error) {
//                 console.error("Error fetching districts:", error);
//             }
//         });
//     } catch (error) {
//         console.error("Error fetching provinces:", error);
//     }
// });

// let districtChangeEventCalled = false;

// const fillDistricts = async (districts, districtSelect, provinceSelect, wardSelect) => {
//     districtSelect.innerHTML = "<option value=''>Chọn quận/huyện</option>";
//     wardSelect.innerHTML = "<option value=''>Chọn phường/xã</option>";

//     districts.forEach(district => {
//         if (district.province_code == provinceSelect.value) {
//             const option = document.createElement("option");
//             option.value = district.code.toString();
//             option.text = district.name;
//             districtSelect.appendChild(option);
//         }
//     });

//     districtSelect.addEventListener("change", async () => {
//         if (!districtChangeEventCalled) {
//             districtChangeEventCalled = true;
//             const selectedDistrictCode = districtSelect.value;

//             try {
//                 const wardResponse = await fetch(`https://provinces.open-api.vn/api/w/?district_code=${selectedDistrictCode}`);
//                 const wards = await wardResponse.json();

//                 fillWards(wards, wardSelect, districtSelect);
//             } catch (error) {
//                 console.error("Error fetching wards:", error);
//             }
//         }
//     });
// }

// const fillWards = (wards, wardSelect, districtSelect) => {
//     wardSelect.innerHTML = "<option value=''>Chọn phường/xã</option>";

//     wards.forEach(ward => {
//         if (ward.district_code == districtSelect.value) {
//             const option = document.createElement("option");
//             option.value = ward.code.toString();
//             option.text = ward.name;
//             wardSelect.appendChild(option);
//         }
//     });
// }
