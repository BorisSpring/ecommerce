import { useMutation, useQueryClient } from "@tanstack/react-query";
import { loginUser as loginUserApi } from "../../api/actions";

export function useLoginUser(setMessage, handleClose) {
  const queryClient = useQueryClient();

  const { mutate: loginUser, isLoading: isLogging } = useMutation({
    mutationFn: async (userCredentials) => {
      const data = await loginUserApi(userCredentials);
      return data;
    },
    onSuccess: (data) => {
      setMessage(() => data?.message);
      if (!data.message) {
        localStorage.setItem("jwt", data.jwt);
        queryClient.invalidateQueries({
          queryKey: ["loggedUser"],
        });
        handleClose();
      }
    },
  });

  return { loginUser, isLogging };
}
