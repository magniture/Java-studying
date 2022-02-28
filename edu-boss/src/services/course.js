/**
 * Courses service
 */

import { request, postRequest, bossBase } from "./common";
export const getCourseById = async courseId => {
  return await request(bossBase + "/course/getCourseById", {
    params: { courseId }
  });
};

//获取课程数据
export const getQueryCourses = async filter => {
  const data = { currentPage: 1, pageSize: 200 };
  // filter
  if (filter.courseName) data.courseName = filter.courseName;
  if (filter.status) data.status = filter.status;

  //return await postRequest(bossBase + "/course/getQueryCourses", data);

  return await postRequest(bossBase + "/course/findAllCourse", data);
};

export const changeState = async (courseId, status) => {
  return await request(bossBase + "/course/changeState", {
    params: { courseId, status }
  });
};

export const saveOrUpdateCourse = async params => {
  // TODO: update logic
  const data = { ...params };
  return await postRequest(bossBase + "/course/saveOrUpdateCourse", data);
};
