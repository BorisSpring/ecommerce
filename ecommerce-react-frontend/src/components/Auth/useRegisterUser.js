import { useMutation, useQueryClient } from "@tanstack/react-query";
import { registerUser as registerUserApi } from "../../api/actions";

export function useRegisterUser(handleClose, setMessage) {
  const queryClient = useQueryClient();

  const { mutate: registerUser, isLoading: isRegistering } = useMutation({
    mutationFn: async (userData) => await registerUserApi(userData),
    onSuccess: (data) => {
      setMessage(data?.message);
      if (data.jwt) {
        localStorage.setItem("jwt", data.jwt);
        handleClose();
        queryClient.invalidateQueries({
          queryKey: ["loggedUser"],
        });
      }
    },
  });
  return { registerUser, isRegistering };
}
