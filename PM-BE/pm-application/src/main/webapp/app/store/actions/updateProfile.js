export const UPDATE_PROFILE = 'UPDATE_PROFILE';

export const updateProfile = (country) => {
    return {
      type: UPDATE_PROFILE,
      country
    };
  };